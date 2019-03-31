package net.toiviainen.servlet.sandbox;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;

/*
 * @WebServlet annotation attributes.
 * name.............The name of the servlet.
 * value............Delegate for urlPatterns.
 * urlPatterns......The URL patterns of the servlet.
 * loadOnstartup....Allows specifying the loading order for servlet(s).
 * initParams.......The array of initialization parameters for the servlet.
 * asyncSupported...Defines whether servlet supports asynchronous operation mode.
 * smallIcon........Small icon for the servlet.
 * largeIcon........Big icon for the servlet.
 * description......Servlet's description.
 * displayName......Servlet's display name.
 */
@SuppressWarnings("serial")
@WebServlet(name = "sandbox servlet name", value = "/", loadOnStartup = 1, asyncSupported = true)
public class SandboxHttpServlet extends HttpServlet {

	private static final String LOG_PREFIX = String.format("[%s] ", SandboxHttpServlet.class.getSimpleName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Servlet 4.0: get information related how request was mapped to servlet.
		HttpServletMapping servletMapping = req.getHttpServletMapping();
		System.out.println(LOG_PREFIX + "HttpServletMapping: ");
		System.out.println(LOG_PREFIX + "\tmappingMatch : " + servletMapping.getMappingMatch());
		System.out.println(LOG_PREFIX + "\tmatchValue   : " + servletMapping.getMatchValue());
		System.out.println(LOG_PREFIX + "\tpattern      : " + servletMapping.getPattern());
		System.out.println(LOG_PREFIX + "\tservletName  : " + servletMapping.getServletName());

		// try to build a push builder so we can pro-actively send resources.
		PushBuilder pushBuilder = req.newPushBuilder();
		boolean pushEnabled = (pushBuilder != null);
		System.out.println(LOG_PREFIX + "Server push enabled: " + pushEnabled);
		if (pushEnabled) {
			pushBuilder.path("image.png");
			pushBuilder.push();
		}

		// build a web page to be shown to user.
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html>");
		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Servlet 4.0 Sandbox</title>");
		builder.append("</head>");
		builder.append("<body>");
		builder.append("<h1>You should see an image right below this text</h1>");
		builder.append("<img src='/servlet-sandbox/image.png' alt='Image'/>");
		builder.append("</body>");
		builder.append("</html>");
		try (PrintWriter writer = resp.getWriter()) {
			writer.write(builder.toString());
		}
		resp.setStatus(HttpServletResponse.SC_OK);
	}

}
