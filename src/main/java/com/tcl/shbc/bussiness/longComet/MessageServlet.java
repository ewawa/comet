package com.tcl.shbc.bussiness.longComet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

public class MessageServlet extends HttpServlet implements CometProcessor {
	private static final long serialVersionUID = 1L;
	private MessageSender msgSender = MessageSender.getInstance();

	/**
	 * @see CometProcessor#event(CometEvent)
	 */
	public void event(CometEvent evt) {
		/**
		 * evt 方法用来处理各种请求，可以根据状态的不同得到各种响应 同时可以不断根据自己的需求向客户端发送信息
		 */
		HttpServletResponse response = evt.getHttpServletResponse();
		HttpServletRequest request = evt.getHttpServletRequest();
		try {
			String sessionId = request.getSession().getId();
			// 对于event而言，会存在多个状态，
			if (evt.getEventType() == CometEvent.EventType.BEGIN) {
				// 在begin的时候操作
				System.out.println(response + "正在建立连接.");
				msgSender.setConn(sessionId, response);
			} else if (evt.getEventType() == CometEvent.EventType.END) {
				// 在结束的时候
				System.out.println(response + "已经结束.");
				msgSender.remove(sessionId, response);
				evt.close();
			} else if (evt.getEventType() == CometEvent.EventType.ERROR) {
				// 在发送错误的时候
				System.out.println(response + "发生错误.");
				msgSender.remove(sessionId, response);
				evt.close();
			} else if (evt.getEventType() == CometEvent.EventType.READ) {
				// 还在读取数据的状态
				throw new RuntimeException("该状态无法操作");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		Thread msgThread = new Thread(msgSender);
		msgThread.setDaemon(true);// 后台进程
		msgThread.start();

		RandomThread rr = new RandomThread();
		Thread rt = new Thread(rr);
		rt.start();
		super.init();
	}

	@Override
	public void destroy() {
		msgSender.close();
		super.destroy();
	}
}

class RandomThread implements Runnable {
	private final static Random ran = new Random(10);
	private boolean running = true;
	private MessageSender msgSender = MessageSender.getInstance();

	@Override
	public void run() {
		try {
			while (running) {
				Thread.sleep(4000);
				int num = ran.nextInt(10);
				System.out.println("产生消息:" + num);
				msgSender.sendMsg(num + "");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
