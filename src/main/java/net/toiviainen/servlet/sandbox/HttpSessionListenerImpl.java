package net.toiviainen.servlet.sandbox;

import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener("A listener for tracing HTTP session creation and destruction.")
public class HttpSessionListenerImpl implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		ServletContext ctx = session.getServletContext();
		List<String> attributeNames = Collections.list(session.getAttributeNames());

		String sessionId = session.getId();
		ctx.log("HTTP session " + sessionId + " is created with the following structure");
		ctx.log("\t\tid                  " + sessionId);
		ctx.log("\t\tisNew               " + session.isNew());
		ctx.log("\t\tcreationTime        " + session.getCreationTime());
		ctx.log("\t\tlastAccessedTime    " + session.getLastAccessedTime());
		ctx.log("\t\tmaxInactiveInterval " + session.getMaxInactiveInterval());
		ctx.log("\t\tattributeNames      " + attributeNames);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		ServletContext ctx = session.getServletContext();
		String sessionId = session.getId();
		ctx.log("HTTP session " + sessionId + " is destroyed");
	}

}
