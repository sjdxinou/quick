package cn.sjd.it.quick.framework.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jquick.core.util.Constants;
import cn.jquick.core.util.StringUtil;
import cn.jquick.core.web.util.WebResourceUtil;

public class ResourceFilter implements Filter {

	private static Logger logger = Logger.getLogger(ResourceFilter.class.getName());

	// Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	// Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filter) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		// HttpSession session = request.getSession();
		//
		// // 是否缓存处理
		// if (null != session) {
		// Object isCache = session.getAttribute(Constants.CLEAR_CACHE_FLAG);
		// if (null == isCache) {
		// String clearCacheFlag = request
		// .getParameter(Constants.CLEAR_CACHE_FLAG);
		// if (null != clearCacheFlag) {
		// session.setAttribute(Constants.CLEAR_CACHE_FLAG,
		// clearCacheFlag);
		// }
		// }
		// }

		// 是否过滤处理
		String noFilterFlag = request.getParameter(Constants.NO_FILTER_FLAG);
		if (!StringUtil.isNullOrEmpty(noFilterFlag)) {
			filter.doFilter(req, resp);
			return;
		}

		logger.log(Level.INFO, "processWebResource begin.");
		// 处理网页资源 处理成功则直接返回 处理失败就继续过滤器链
		if (!WebResourceUtil.processWebResource(request, response)) {
			logger.log(Level.INFO, "processWebResource:false[filter.doFilter(req, resp);]");
			filter.doFilter(req, resp);
			return;
		}

	}

	// Override
	public void init(FilterConfig cfg) throws ServletException {
		// TODO Auto-generated method stub

	}

}
