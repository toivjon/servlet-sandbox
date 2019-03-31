package net.toiviainen.servlet.sandbox;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "Sandbox Filter", urlPatterns = "/*")
public class FilterImpl implements Filter {

	private static final String LOG_PREFIX = String.format("[%s] ", FilterImpl.class.getSimpleName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		List<String> initParameterNames = Collections.list(filterConfig.getInitParameterNames());

		System.out.println(LOG_PREFIX + "Filter initialized with the following definitions");
		System.out.println(LOG_PREFIX + "\t\tname " + filterConfig.getFilterName());
		System.out.println(LOG_PREFIX + "\t\tinitParameterNames " + initParameterNames);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println(LOG_PREFIX + "Executing filter...");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println(LOG_PREFIX + "Filter is being destroyed");
	}

}
