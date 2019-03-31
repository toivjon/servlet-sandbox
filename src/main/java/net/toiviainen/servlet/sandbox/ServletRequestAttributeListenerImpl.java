package net.toiviainen.servlet.sandbox;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener("A listener to trace servlet request attribute changes.")
public class ServletRequestAttributeListenerImpl implements ServletRequestAttributeListener {

	private static final String LOG_PREFIX = String.format("[%s] ",
			ServletRequestAttributeListenerImpl.class.getSimpleName());

	/** The pattern used to print the servlet context attribute. */
	private static final String ATTRIBUTE_PATTERN = "%s : %s";

	@Override
	public void attributeAdded(ServletRequestAttributeEvent srae) {
		System.out.println(LOG_PREFIX + "ServletRequest attribute added");
		System.out.println(LOG_PREFIX + "\t\t" + attribute(srae));
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		System.out.println(LOG_PREFIX + "ServletRequest attribute removed");
		System.out.println(LOG_PREFIX + "\t\t" + attribute(srae));
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		System.out.println(LOG_PREFIX + "ServletRequest attribute replaced");
		System.out.println(LOG_PREFIX + "\t\t" + attribute(srae));
	}

	/**
	 * Get a string presentation of the attribute that issued the event.
	 *
	 * @param event The attribute event that was issued.
	 * @return A string presentation of the attribute.
	 */
	private static String attribute(ServletRequestAttributeEvent event) {
		String name = event.getName();
		Object value = event.getValue();
		return String.format(ATTRIBUTE_PATTERN, name, value);
	}
}
