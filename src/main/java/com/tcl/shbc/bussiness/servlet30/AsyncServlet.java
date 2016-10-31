package com.tcl.shbc.bussiness.servlet30;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/Async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
	private static final long serialVersionUID = 822178713133426493L;
	private final static int DEFAULT_TIME_OUT = 10 * 60 * 1000;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		final AsyncContext actx = req.startAsync();
		actx.setTimeout(DEFAULT_TIME_OUT);
		actx.addListener(new AsyncListener() {
			@Override
			public void onComplete(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				ClientComet.getInstance().removeAsyncContext(actx);
				System.out.println("AsyncListener-->onComplete");
			}

			@Override
			public void onError(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				ClientComet.getInstance().removeAsyncContext(actx);
				System.out.println("AsyncListener-->onError");
			}

			@Override
			public void onStartAsync(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				System.out.println("AsyncListener-->onStartAsync");
			}

			@Override
			public void onTimeout(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				ClientComet.getInstance().removeAsyncContext(actx);
				System.out.println("AsyncListener-->onTimeout");
			}
		});
		ClientComet.getInstance().addAsyncContext(actx);
	}
}
