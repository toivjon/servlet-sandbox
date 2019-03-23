package net.toiviainen.servlet.sandbox;

import static java.lang.String.format;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener("A listener to trace servlet context creation and destruction.")
public class ServletContextListenerImpl implements ServletContextListener {

	/** A pattern to be used to print out servlet version strings. */
	private static final String VERSION_PATTERN = "%d.%d";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();
		ctx.log("ServletContext initialized");
		ctx.log("\t\tcontextPath        " + ctx.getContextPath());
		ctx.log("\t\tversion            " + version(ctx));
		ctx.log("\t\teffectiveVersion   " + effectiveVersion(ctx));
		ctx.log("\t\tserverInfo         " + ctx.getServerInfo());
		ctx.log("\t\tservletContextName " + ctx.getServletContextName());
		ctx.log("\t\tvirtualServerName  " + ctx.getVirtualServerName());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();
		ctx.log("ServletContext destroyed");
	}

	/**
	 * Get the effective version of the servlet as a simple {@link String}.
	 *
	 * @param ctx A reference to target servlet context.
	 * @return The effective servlet version as a string.
	 */
	private static String effectiveVersion(ServletContext ctx) {
		int major = ctx.getEffectiveMajorVersion();
		int minor = ctx.getEffectiveMinorVersion();
		return format(VERSION_PATTERN, major, minor);
	}

	/**
	 * Get the version of the servlet as a simple {@link String}.
	 *
	 * @param ctx A reference to target servlet context.
	 * @return The servlet version as a string.
	 */
	private static String version(ServletContext ctx) {
		int major = ctx.getMajorVersion();
		int minor = ctx.getMinorVersion();
		return format(VERSION_PATTERN, major, minor);
	}

}
