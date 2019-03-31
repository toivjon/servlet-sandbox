package net.toiviainen.servlet.sandbox;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener("A listener to trace HTTP session attribute changes.")
public class HttpSessionAttributeListenerImpl implements HttpSessionAttributeListener {

	private static final String LOG_PREFIX = String.format("[%s] ", FilterImpl.class.getSimpleName());

	/** The pattern used to print the servlet context attribute. */
	private static final String ATTRIBUTE_PATTERN = "%s : %s";

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		String sessionId = session.getId();
		System.out.println(LOG_PREFIX + "HttpSession " + sessionId + " attribute added");
		System.out.println(LOG_PREFIX + "\t\t" + attribute(event));
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		String sessionId = session.getId();
		System.out.println(LOG_PREFIX + "HttpSession " + sessionId + " attribute removed");
		System.out.println(LOG_PREFIX + "\t\t" + attribute(event));
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		String sessionId = session.getId();
		System.out.println(LOG_PREFIX + "HttpSession " + sessionId + " attribute replaced");
		System.out.println(LOG_PREFIX + "\t\t" + attribute(event));
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
