package net.toiviainen.servlet.sandbox;

import static java.lang.String.format;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.annotation.WebListener;

@WebListener("A listener to trace servlet context creation and destruction.")
public class ServletContextListenerImpl implements ServletContextListener {

	/** A pattern to be used to print out servlet version strings. */
	private static final String VERSION_PATTERN = "%d.%d";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// get a reference to servlet context contents.
		ServletContext ctx = sce.getServletContext();
		Map<String, ? extends ServletRegistration> servlets = ctx.getServletRegistrations();
		Map<String, ? extends FilterRegistration> filters = ctx.getFilterRegistrations();
		SessionCookieConfig sessionCookieConfig = ctx.getSessionCookieConfig();
		List<String> initParameterNames = Collections.list(ctx.getInitParameterNames());

		ctx.log("ServletContext initialized with the following environment");
		ctx.log("\t\tcontextPath                   " + ctx.getContextPath());
		ctx.log("\t\tversion                       " + version(ctx));
		ctx.log("\t\teffectiveVersion              " + effectiveVersion(ctx));
		ctx.log("\t\tserverInfo                    " + ctx.getServerInfo());
		ctx.log("\t\tservletContextName            " + ctx.getServletContextName());
		ctx.log("\t\tvirtualServerName             " + ctx.getVirtualServerName());
		ctx.log("\t\tdefaultSessionTrackingModes   " + ctx.getDefaultSessionTrackingModes());
		ctx.log("\t\teffectiveSessionTrackingModes " + ctx.getEffectiveSessionTrackingModes());
		ctx.log("\t\tservletRegistrations          " + servlets.size());
		ctx.log("\t\tfilterRegistrations           " + filters.size());
		ctx.log("\t\tsessionCookieName             " + sessionCookieConfig.getName());
		ctx.log("\t\tsessionCookieMaxAge           " + sessionCookieConfig.getMaxAge());
		ctx.log("\t\tsessionCookieDomain           " + sessionCookieConfig.getDomain());
		ctx.log("\t\tsessionCookiePath             " + sessionCookieConfig.getPath());
		ctx.log("\t\tsessionCookieComment          " + sessionCookieConfig.getComment());
		ctx.log("\t\tinitParameterNames            " + initParameterNames);

		// print all registered servlet instances along with their base information.
		for (ServletRegistration registration : servlets.values()) {
			ctx.log("\t\tServlet");
			ctx.log("\t\t\tname      " + registration.getName());
			ctx.log("\t\t\tclass     " + registration.getClassName());
			ctx.log("\t\t\trunAsRole " + registration.getRunAsRole());
			ctx.log("\t\t\tmappings  " + registration.getMappings());
		}

		// print all registered servlet filters along with their base information.
		for (FilterRegistration registration : filters.values()) {
			ctx.log("\t\tFilter");
			ctx.log("\t\t\tname                " + registration.getName());
			ctx.log("\t\t\tclassName           " + registration.getClassName());
			ctx.log("\t\t\tservletNameMappings " + registration.getServletNameMappings());
			ctx.log("\t\t\turlPatternMappings  " + registration.getUrlPatternMappings());
		}
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
