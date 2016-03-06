package misc;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XssFilter implements Filter {
	private FilterConfig __fc;

	public void destroy() {
		__fc = null; 
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new RequestWrapper((HttpServletRequest) req), resp);
	}

	public void init(FilterConfig fc) throws ServletException {
		__fc = fc;
	}
}
