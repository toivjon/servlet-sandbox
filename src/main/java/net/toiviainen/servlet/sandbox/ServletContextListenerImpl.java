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

	private static final String LOG_PREFIX = String.format("[%s] ", ServletContextListenerImpl.class.getSimpleName());

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

		System.out.println(LOG_PREFIX + "ServletContext initialized with the following environment");
		System.out.println(LOG_PREFIX + "\t\tcontextPath                   " + ctx.getContextPath());
		System.out.println(LOG_PREFIX + "\t\tversion                       " + version(ctx));
		System.out.println(LOG_PREFIX + "\t\teffectiveVersion              " + effectiveVersion(ctx));
		System.out.println(LOG_PREFIX + "\t\tserverInfo                    " + ctx.getServerInfo());
		System.out.println(LOG_PREFIX + "\t\tservletContextName            " + ctx.getServletContextName());
		System.out.println(LOG_PREFIX + "\t\tvirtualServerName             " + ctx.getVirtualServerName());
		System.out.println(LOG_PREFIX + "\t\tdefaultSessionTrackingModes   " + ctx.getDefaultSessionTrackingModes());
		System.out.println(LOG_PREFIX + "\t\teffectiveSessionTrackingModes " + ctx.getEffectiveSessionTrackingModes());
		System.out.println(LOG_PREFIX + "\t\tservletRegistrations          " + servlets.size());
		System.out.println(LOG_PREFIX + "\t\tfilterRegistrations           " + filters.size());
		System.out.println(LOG_PREFIX + "\t\tsessionCookieName             " + sessionCookieConfig.getName());
		System.out.println(LOG_PREFIX + "\t\tsessionCookieMaxAge           " + sessionCookieConfig.getMaxAge());
		System.out.println(LOG_PREFIX + "\t\tsessionCookieDomain           " + sessionCookieConfig.getDomain());
		System.out.println(LOG_PREFIX + "\t\tsessionCookiePath             " + sessionCookieConfig.getPath());
		System.out.println(LOG_PREFIX + "\t\tsessionCookieComment          " + sessionCookieConfig.getComment());
		System.out.println(LOG_PREFIX + "\t\tinitParameterNames            " + initParameterNames);

		// print all registered servlet instances along with their base information.
		for (ServletRegistration registration : servlets.values()) {
			System.out.println(LOG_PREFIX + "\t\tServlet");
			System.out.println(LOG_PREFIX + "\t\t\tname      " + registration.getName());
			System.out.println(LOG_PREFIX + "\t\t\tclass     " + registration.getClassName());
			System.out.println(LOG_PREFIX + "\t\t\trunAsRole " + registration.getRunAsRole());
			System.out.println(LOG_PREFIX + "\t\t\tmappings  " + registration.getMappings());
		}

		// print all registered servlet filters along with their base information.
		for (FilterRegistration registration : filters.values()) {
			System.out.println(LOG_PREFIX + "\t\tFilter");
			System.out.println(LOG_PREFIX + "\t\t\tname                " + registration.getName());
			System.out.println(LOG_PREFIX + "\t\t\tclassName           " + registration.getClassName());
			System.out.println(LOG_PREFIX + "\t\t\tservletNameMappings " + registration.getServletNameMappings());
			System.out.println(LOG_PREFIX + "\t\t\turlPatternMappings  " + registration.getUrlPatternMappings());
		}

		// specify session timeout (4.0 specific addition).
		ctx.setSessionTimeout(1);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println(LOG_PREFIX + "ServletContext destroyed");
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
