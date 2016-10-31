package com.tcl.shbc.bussiness.servlet30;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;

public class ClientComet {
	private static ClientComet instance;
	private ConcurrentLinkedQueue<AsyncContext> actxQueue;
	private LinkedBlockingQueue<Javascript> mesgQueue;

	private ClientComet() {
		actxQueue = new ConcurrentLinkedQueue<AsyncContext>();
		mesgQueue = new LinkedBlockingQueue<Javascript>();
		new ClientCometThread().start();
	}

	public static ClientComet getInstance() {
		if (instance == null) {
			instance = new ClientComet();
		}
		return instance;
	}

	public void addAsyncContext(AsyncContext actx) {
		actxQueue.add(actx);
	}

	public void removeAsyncContext(AsyncContext actx) {
		actxQueue.remove();
	}

	public void callClient(Javascript javascript) {
		mesgQueue.add(javascript);
	}

	protected class ClientCometThread extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					Javascript javascript = mesgQueue.take();
					for (AsyncContext actx : actxQueue) {
						PrintWriter writer = actx.getResponse().getWriter();
						writer.write(javascript.getScript());
						writer.flush();
						System.out.println("ClientCometThread-->sendJavaScript");
					}
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
