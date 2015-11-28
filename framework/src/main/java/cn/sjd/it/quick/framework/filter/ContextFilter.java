package cn.sjd.it.quick.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.jquick.core.context.ReqContextUtil;

public class ContextFilter implements Filter {

	public final static String CONTEXT_REQUEST = "__jquick_context_request__";
	public final static String CONTEXT_RESPONSE = "__jquick_context_response__";

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		ReqContextUtil.processContext((HttpServletRequest) req);

		// 用于处理dojo分页
		ReqContextUtil.getCurrentContext().getItems().put(CONTEXT_REQUEST, req);
		ReqContextUtil.getCurrentContext().getItems().put(CONTEXT_RESPONSE, resp);
		chain.doFilter(req, resp);
		ReqContextUtil.getCurrentContext().getItems().clear();
		ReqContextUtil.removeContext();

	}

	public void init(FilterConfig cfg) throws ServletException {
	}

}
