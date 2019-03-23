package net.toiviainen.servlet.sandbox;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener("A listener to trace servlet context attribute changes.")
public class ServletContextAttributeListenerImpl implements ServletContextAttributeListener {

	/** The pattern used to print the servlet context attribute. */
	private static final String ATTRIBUTE_PATTERN = "%s : %s";

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		ServletContext ctx = event.getServletContext();
		ctx.log("ServletContext attribute added");
		ctx.log("\t\t" + attribute(event));
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		ServletContext ctx = event.getServletContext();
		ctx.log("ServletContext attribute removed");
		ctx.log("\t\t" + attribute(event));
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		ServletContext ctx = event.getServletContext();
		ctx.log("ServletContext attribute replaced");
		ctx.log("\t\t" + attribute(event));
	}

	/**
	 * Get a string presentation of the attribute that issued the event.
	 *
	 * @param event The attribute event that was issued.
	 * @return A string presentation of the attribute.
	 */
	private static String attribute(ServletContextAttributeEvent event) {
		String name = event.getName();
		Object value = event.getValue();
		return String.format(ATTRIBUTE_PATTERN, name, value);
	}

}
