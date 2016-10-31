package com.tcl.shbc.bussiness.longComet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

public class MessageSender implements Runnable {
	private String msg = new String();
	Map<String, List<HttpServletResponse>> conns = new HashMap<String, List<HttpServletResponse>>();
	public boolean running = true;

	private static MessageSender messageSender = new MessageSender();

	private MessageSender() {
	}

	public static MessageSender getInstance() {
		return messageSender;
	}

	public void close() {
		running = false;
	}

	public synchronized void sendMsg(String msg) {
		this.msg = msg;
		this.notify();
	}

	public void remove(String sessionId, HttpServletResponse response) {
		synchronized (this.conns) {
			conns.get(sessionId).remove(response);
		}
	}

	public void setConn(String sessionId, HttpServletResponse conn) {
		synchronized (this.conns) {
			List<HttpServletResponse> _responses = this.conns.get(sessionId);
			if (_responses == null) {
				_responses = new ArrayList<HttpServletResponse>();
			}
			_responses.add(conn);
			conns.put(sessionId, _responses);
		}
	}

	public List<HttpServletResponse> getAllResponse() {
		List<HttpServletResponse> _allResponses = new ArrayList<HttpServletResponse>();
		Set<String> _sessionIds = conns.keySet();
		for (String sessionId : _sessionIds) {
			_allResponses.addAll(conns.get(sessionId));
		}
		return _allResponses;
	}

	@Override
	public void run() {
		try {
			while (running) {
				String msg = "";
				if (this.msg == null || this.msg == "") {
					synchronized (this) {
						this.wait();
					}
				}
				msg = this.msg;
				this.msg = null;
				synchronized (this.conns) {
					List<HttpServletResponse> responses = this.getAllResponse();
					for (HttpServletResponse response : responses) {
						response.setContentType("text/html");
						PrintWriter out = response.getWriter();
						System.out.println("发送消息:" + msg);
						out.print(msg);
						out.flush();
						out.close();
						out = null;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
