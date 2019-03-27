package net.toiviainen.servlet.sandbox;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@WebListener("A listener to trace servlet request creation and destruction.")
public class ServletRequestListenerImpl implements ServletRequestListener {

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		// get a reference to servlet request contents.
		ServletContext ctx = sre.getServletContext();
		ServletRequest request = sre.getServletRequest();
		Optional<AsyncContext> asyncCtx = safeGetAsyncContext(request);
		List<Locale> locales = Collections.list(request.getLocales());
		List<String> parameterNames = Collections.list(request.getParameterNames());

		ctx.log("Servlet request initialized with the following structure");
		ctx.log("\t\tclass             " + request.getClass());
		ctx.log("\t\tcharacterEncoding " + request.getCharacterEncoding());
		ctx.log("\t\tcontentLength     " + request.getContentLength());
		ctx.log("\t\tcontentLengthLong " + request.getContentLengthLong());
		ctx.log("\t\tcontentType       " + request.getContentType());
		ctx.log("\t\tlocalAddr         " + request.getLocalAddr());
		ctx.log("\t\tlocalName         " + request.getLocalName());
		ctx.log("\t\tlocalPort         " + request.getLocalPort());
		ctx.log("\t\tprotocol          " + request.getProtocol());
		ctx.log("\t\tremoteAddr        " + request.getRemoteAddr());
		ctx.log("\t\tremoteHost        " + request.getRemoteHost());
		ctx.log("\t\tremotePort        " + request.getRemotePort());
		ctx.log("\t\tscheme            " + request.getScheme());
		ctx.log("\t\tserverName		   " + request.getServerName());
		ctx.log("\t\tserverPort        " + request.getServerPort());
		ctx.log("\t\tisSecure          " + request.isSecure());
		ctx.log("\t\tisAsyncStarted    " + request.isAsyncStarted());
		ctx.log("\t\tisAsyncSupported  " + request.isAsyncSupported());
		ctx.log("\t\tlocales           " + locales);
		ctx.log("\t\tpreferredLocale   " + request.getLocale());
		ctx.log("\t\tparameterNames    " + parameterNames);
		ctx.log("\t\tdispatcherType    " + request.getDispatcherType());
		if (asyncCtx.isPresent()) {
			AsyncContext asyncContext = asyncCtx.get();
			ctx.log("\t\tasyncTimeout      " + asyncContext.getTimeout());
			ctx.log("\t\tasyncOriginals    " + asyncContext.hasOriginalRequestAndResponse());
		}
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			List<String> headerNames = Collections.list(httpRequest.getHeaderNames());
			ctx.log("\t\tauthType          " + httpRequest.getAuthType());
			ctx.log("\t\tcharacterEncoding " + httpRequest.getCharacterEncoding());
			ctx.log("\t\tmethod            " + httpRequest.getMethod());
			ctx.log("\t\tpathInfo          " + httpRequest.getPathInfo());
			ctx.log("\t\tpathTranslated    " + httpRequest.getPathTranslated());
			ctx.log("\t\tcontextPath       " + httpRequest.getContextPath());
			ctx.log("\t\tqueryString       " + httpRequest.getQueryString());
			ctx.log("\t\tremoteUser        " + httpRequest.getRemoteUser());
			ctx.log("\t\tuserPrincipal     " + httpRequest.getUserPrincipal());
			ctx.log("\t\trequestedSid      " + httpRequest.getRequestedSessionId());
			ctx.log("\t\trequestURI        " + httpRequest.getRequestURI());
			ctx.log("\t\trequestURL        " + httpRequest.getRequestURL());
			ctx.log("\t\tservletPath       " + httpRequest.getServletPath());
			ctx.log("\t\tisSidValid        " + httpRequest.isRequestedSessionIdValid());
			ctx.log("\t\tisSidFromCookie   " + httpRequest.isRequestedSessionIdFromCookie());
			ctx.log("\t\tisSidFromURL      " + httpRequest.isRequestedSessionIdFromURL());

			ctx.log("\t\theaders:");
			for (String headerName : headerNames) {
				ctx.log("\t\t\t" + headerName + ": " + Collections.list(httpRequest.getHeaders(headerName)));
			}

			ctx.log("\t\tcookies:");
			if (httpRequest.getCookies() != null) {
				for (Cookie cookie : httpRequest.getCookies()) {
					ctx.log("\t\t\tname    " + cookie.getName());
					ctx.log("\t\t\tdomain  " + cookie.getDomain());
					ctx.log("\t\t\tmaxAge  " + cookie.getMaxAge());
					ctx.log("\t\t\tpath    " + cookie.getPath());
					ctx.log("\t\t\tvalue   " + cookie.getValue());
					ctx.log("\t\t\tversion " + cookie.getVersion());
					ctx.log("\t\t\tsecure  " + cookie.getSecure());
					ctx.log("");
				}
			}
		}
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		ServletContext ctx = sre.getServletContext();
		ctx.log("Servlet request destructed");
	}

	/**
	 * Return the asynchronous context from the provided request.
	 * <p>
	 * Typically getting the context throws exception whether a-sync is not enabled.
	 * This function will catch that exception and return an empty optional instead.
	 * <p>
	 *
	 * @param request The target request.
	 * @return The context from the request.
	 */
	private static Optional<AsyncContext> safeGetAsyncContext(ServletRequest request) {
		try {
			return Optional.ofNullable(request.getAsyncContext());
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}
