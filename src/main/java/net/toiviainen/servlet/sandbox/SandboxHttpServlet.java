package net.toiviainen.servlet.sandbox;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@WebServlet(name = "sandbox servlet name", value = "sandbox", loadOnStartup = 1, asyncSupported = true)
public class SandboxHttpServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
	}

}
