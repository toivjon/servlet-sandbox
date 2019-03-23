package net.toiviainen.servlet.sandbox;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;

@WebListener("A listener to trace HTTP session identifier changes.")
public class HttpSessionIdListenerImpl implements HttpSessionIdListener {

	@Override
	public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
		HttpSession session = event.getSession();
		ServletContext ctx = session.getServletContext();
		ctx.log("HTTP session " + session.getId() + " identifier changed to " + oldSessionId);
	}

}
