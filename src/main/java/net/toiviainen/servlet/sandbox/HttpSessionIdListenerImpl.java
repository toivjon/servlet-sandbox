package net.toiviainen.servlet.sandbox;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;

@WebListener("A listener to trace HTTP session identifier changes.")
public class HttpSessionIdListenerImpl implements HttpSessionIdListener {

	private static final String LOG_PREFIX = String.format("[%s] ", HttpSessionIdListenerImpl.class.getSimpleName());

	@Override
	public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
		HttpSession session = event.getSession();
		System.out.println(LOG_PREFIX + "HTTP session " + session.getId() + " identifier changed to " + oldSessionId);
	}

}
