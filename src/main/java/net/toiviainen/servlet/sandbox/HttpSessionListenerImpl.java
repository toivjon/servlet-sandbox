package net.toiviainen.servlet.sandbox;

import java.util.Collections;
import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener("A listener for tracing HTTP session creation and destruction.")
public class HttpSessionListenerImpl implements HttpSessionListener {

	private static final String LOG_PREFIX = String.format("[%s] ", HttpSessionListenerImpl.class.getSimpleName());

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		List<String> attributeNames = Collections.list(session.getAttributeNames());

		String sessionId = session.getId();
		System.out.println(LOG_PREFIX + "HTTP session " + sessionId + " is created with the following structure");
		System.out.println(LOG_PREFIX + "\t\tid                  " + sessionId);
		System.out.println(LOG_PREFIX + "\t\tisNew               " + session.isNew());
		System.out.println(LOG_PREFIX + "\t\tcreationTime        " + session.getCreationTime());
		System.out.println(LOG_PREFIX + "\t\tlastAccessedTime    " + session.getLastAccessedTime());
		System.out.println(LOG_PREFIX + "\t\tmaxInactiveInterval " + session.getMaxInactiveInterval());
		System.out.println(LOG_PREFIX + "\t\tattributeNames      " + attributeNames);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		String sessionId = session.getId();
		System.out.println(LOG_PREFIX + "HTTP session " + sessionId + " is destroyed");
	}

}
