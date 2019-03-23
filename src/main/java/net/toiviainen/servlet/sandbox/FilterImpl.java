package net.toiviainen.servlet.sandbox;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "Sandbox Filter", urlPatterns = "/*")
public class FilterImpl implements Filter {

	private ServletContext ctx;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.ctx = filterConfig.getServletContext();
		List<String> initParameterNames = Collections.list(filterConfig.getInitParameterNames());

		ctx.log("Filter initialized with the following definitions");
		ctx.log("\t\tname " + filterConfig.getFilterName());
		ctx.log("\t\tinitParameterNames " + initParameterNames);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ctx.log("Executing filter...");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		ctx.log("Filter is being destroyed");
	}

}
