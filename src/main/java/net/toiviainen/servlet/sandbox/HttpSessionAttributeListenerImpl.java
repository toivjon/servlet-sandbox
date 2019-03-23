package net.toiviainen.servlet.sandbox;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener("A listener to trace HTTP session attribute changes.")
public class HttpSessionAttributeListenerImpl implements HttpSessionAttributeListener {

	/** The pattern used to print the servlet context attribute. */
	private static final String ATTRIBUTE_PATTERN = "%s : %s";

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		String sessionId = session.getId();
		ServletContext ctx = session.getServletContext();
		ctx.log("HttpSession " + sessionId + " attribute added");
		ctx.log("\t\t" + attribute(event));
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		String sessionId = session.getId();
		ServletContext ctx = session.getServletContext();
		ctx.log("HttpSession " + sessionId + " attribute removed");
		ctx.log("\t\t" + attribute(event));
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		String sessionId = session.getId();
		ServletContext ctx = session.getServletContext();
		ctx.log("HttpSession " + sessionId + " attribute replaced");
		ctx.log("\t\t" + attribute(event));
	}

	/**
	 * Get a string presentation of the attribute that issued the event.
	 *
	 * @param event The attribute event that was issued.
	 * @return A string presentation of the attribute.
	 */
	private static String attribute(HttpSessionBindingEvent event) {
		String name = event.getName();
		Object value = event.getValue();
		return String.format(ATTRIBUTE_PATTERN, name, value);
	}
}
