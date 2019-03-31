package net.toiviainen.servlet.sandbox;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@WebListener("A listener to trace servlet request creation and destruction.")
public class ServletRequestListenerImpl implements ServletRequestListener {

	private static final String LOG_PREFIX = String.format("[%s] ", ServletRequestListenerImpl.class.getSimpleName());

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		// get a reference to servlet request contents.
		ServletRequest request = sre.getServletRequest();
		Optional<AsyncContext> asyncCtx = safeGetAsyncContext(request);
		List<Locale> locales = Collections.list(request.getLocales());
		List<String> parameterNames = Collections.list(request.getParameterNames());

		System.out.println(LOG_PREFIX + "Servlet request initialized with the following structure");
		System.out.println(LOG_PREFIX + "\t\tclass             " + request.getClass());
		System.out.println(LOG_PREFIX + "\t\tcharacterEncoding " + request.getCharacterEncoding());
		System.out.println(LOG_PREFIX + "\t\tcontentLength     " + request.getContentLength());
		System.out.println(LOG_PREFIX + "\t\tcontentLengthLong " + request.getContentLengthLong());
		System.out.println(LOG_PREFIX + "\t\tcontentType       " + request.getContentType());
		System.out.println(LOG_PREFIX + "\t\tlocalAddr         " + request.getLocalAddr());
		System.out.println(LOG_PREFIX + "\t\tlocalName         " + request.getLocalName());
		System.out.println(LOG_PREFIX + "\t\tlocalPort         " + request.getLocalPort());
		System.out.println(LOG_PREFIX + "\t\tprotocol          " + request.getProtocol());
		System.out.println(LOG_PREFIX + "\t\tremoteAddr        " + request.getRemoteAddr());
		System.out.println(LOG_PREFIX + "\t\tremoteHost        " + request.getRemoteHost());
		System.out.println(LOG_PREFIX + "\t\tremotePort        " + request.getRemotePort());
		System.out.println(LOG_PREFIX + "\t\tscheme            " + request.getScheme());
		System.out.println(LOG_PREFIX + "\t\tserverName		   " + request.getServerName());
		System.out.println(LOG_PREFIX + "\t\tserverPort        " + request.getServerPort());
		System.out.println(LOG_PREFIX + "\t\tisSecure          " + request.isSecure());
		System.out.println(LOG_PREFIX + "\t\tisAsyncStarted    " + request.isAsyncStarted());
		System.out.println(LOG_PREFIX + "\t\tisAsyncSupported  " + request.isAsyncSupported());
		System.out.println(LOG_PREFIX + "\t\tlocales           " + locales);
		System.out.println(LOG_PREFIX + "\t\tpreferredLocale   " + request.getLocale());
		System.out.println(LOG_PREFIX + "\t\tparameterNames    " + parameterNames);
		System.out.println(LOG_PREFIX + "\t\tdispatcherType    " + request.getDispatcherType());
		if (asyncCtx.isPresent()) {
			AsyncContext asyncContext = asyncCtx.get();
			System.out.println(LOG_PREFIX + "\t\tasyncTimeout      " + asyncContext.getTimeout());
			System.out.println(LOG_PREFIX + "\t\tasyncOriginals    " + asyncContext.hasOriginalRequestAndResponse());
		}
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			List<String> headerNames = Collections.list(httpRequest.getHeaderNames());
			System.out.println(LOG_PREFIX + "\t\tauthType          " + httpRequest.getAuthType());
			System.out.println(LOG_PREFIX + "\t\tcharacterEncoding " + httpRequest.getCharacterEncoding());
			System.out.println(LOG_PREFIX + "\t\tmethod            " + httpRequest.getMethod());
			System.out.println(LOG_PREFIX + "\t\tpathInfo          " + httpRequest.getPathInfo());
			System.out.println(LOG_PREFIX + "\t\tpathTranslated    " + httpRequest.getPathTranslated());
			System.out.println(LOG_PREFIX + "\t\tcontextPath       " + httpRequest.getContextPath());
			System.out.println(LOG_PREFIX + "\t\tqueryString       " + httpRequest.getQueryString());
			System.out.println(LOG_PREFIX + "\t\tremoteUser        " + httpRequest.getRemoteUser());
			System.out.println(LOG_PREFIX + "\t\tuserPrincipal     " + httpRequest.getUserPrincipal());
			System.out.println(LOG_PREFIX + "\t\trequestedSid      " + httpRequest.getRequestedSessionId());
			System.out.println(LOG_PREFIX + "\t\trequestURI        " + httpRequest.getRequestURI());
			System.out.println(LOG_PREFIX + "\t\trequestURL        " + httpRequest.getRequestURL());
			System.out.println(LOG_PREFIX + "\t\tservletPath       " + httpRequest.getServletPath());
			System.out.println(LOG_PREFIX + "\t\tisSidValid        " + httpRequest.isRequestedSessionIdValid());
			System.out.println(LOG_PREFIX + "\t\tisSidFromCookie   " + httpRequest.isRequestedSessionIdFromCookie());
			System.out.println(LOG_PREFIX + "\t\tisSidFromURL      " + httpRequest.isRequestedSessionIdFromURL());

			System.out.println(LOG_PREFIX + "\t\theaders:");
			for (String headerName : headerNames) {
				System.out.println(LOG_PREFIX + "\t\t\t" + headerName + ": "
						+ Collections.list(httpRequest.getHeaders(headerName)));
			}

			System.out.println(LOG_PREFIX + "\t\tcookies:");
			if (httpRequest.getCookies() != null) {
				for (Cookie cookie : httpRequest.getCookies()) {
					System.out.println(LOG_PREFIX + "\t\t\tname    " + cookie.getName());
					System.out.println(LOG_PREFIX + "\t\t\tdomain  " + cookie.getDomain());
					System.out.println(LOG_PREFIX + "\t\t\tmaxAge  " + cookie.getMaxAge());
					System.out.println(LOG_PREFIX + "\t\t\tpath    " + cookie.getPath());
					System.out.println(LOG_PREFIX + "\t\t\tvalue   " + cookie.getValue());
					System.out.println(LOG_PREFIX + "\t\t\tversion " + cookie.getVersion());
					System.out.println(LOG_PREFIX + "\t\t\tsecure  " + cookie.getSecure());
					System.out.println(LOG_PREFIX + "");
				}
			}
		}
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		System.out.println(LOG_PREFIX + "Servlet request destructed");
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
