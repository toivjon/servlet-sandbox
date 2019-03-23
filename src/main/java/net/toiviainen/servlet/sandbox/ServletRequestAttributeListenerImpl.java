package net.toiviainen.servlet.sandbox;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener("A listener to trace servlet request attribute changes.")
public class ServletRequestAttributeListenerImpl implements ServletRequestAttributeListener {

	/** The pattern used to print the servlet context attribute. */
	private static final String ATTRIBUTE_PATTERN = "%s : %s";

	@Override
	public void attributeAdded(ServletRequestAttributeEvent srae) {
		ServletContext ctx = srae.getServletContext();
		ctx.log("ServletRequest attribute added");
		ctx.log("\t\t" + attribute(srae));
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		ServletContext ctx = srae.getServletContext();
		ctx.log("ServletRequest attribute removed");
		ctx.log("\t\t" + attribute(srae));
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		ServletContext ctx = srae.getServletContext();
		ctx.log("ServletRequest attribute replaced");
		ctx.log("\t\t" + attribute(srae));
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
