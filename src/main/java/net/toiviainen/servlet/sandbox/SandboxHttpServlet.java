package net.toiviainen.servlet.sandbox;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	// http://alibassam.com/everything-new-servlet-4-0/

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SandboxHttpServlet#doGet");

		// Servlet 4.0: get information related how request was mapped to servlet.
		HttpServletMapping servletMapping = req.getHttpServletMapping();
		System.out.println("HttpServletMapping: ");
		System.out.println("\tmappingMatch : " + servletMapping.getMappingMatch());
		System.out.println("\tmatchValue   : " + servletMapping.getMatchValue());
		System.out.println("\tpattern      : " + servletMapping.getPattern());
		System.out.println("\tservletName  : " + servletMapping.getServletName());

		// debug and refresh HTTP session value each time we arrive.
		ServletContext ctx = getServletContext();
		HttpSession session = req.getSession();
		ctx.log("A value from HTTP session: " + session.getAttribute("foo"));
		session.setAttribute("foo", Instant.now());
	}

}
