package cn.sjd.it.quick.framework.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jquick.core.util.Constants;
import cn.jquick.core.util.StreamUtil;
import cn.jquick.core.util.StringUtil;

public class WebResourceUtil {

	/**
	 * .
	 */
	private static final String SEPARATOR = ".";

	private static Logger logger = Logger.getLogger(WebResourceUtil.class.getName());

	/**
	 * 默认资源文件缓存天数
	 */
	private final static int CACHE_DAYS = 1 * 24 * 60 * 60;

	private WebResourceUtil() {
	}

	// private static WebResourceUtil self = new WebResourceUtil();
	//
	// public static WebResourceUtil getInstance() {
	// return self;
	// }

	/**
	 * 处理请求资源
	 * 
	 * @param request
	 * @param response
	 */
	public static boolean processWebResource(HttpServletRequest request, HttpServletResponse response) {

		// 获取访问资源路径
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		if (StringUtil.isNullOrEmpty(uri)) {
			return false;
		}
		int index = 0;
		int endIndex = uri.length();
		int tmpEndIdx = uri.indexOf(";");
		if (tmpEndIdx != -1) {
			endIndex = tmpEndIdx;
		}
		if (!StringUtil.isNullOrEmpty(contextPath)) {
			index = contextPath.length();
		}
		String requestPath = uri.substring(index, endIndex);
		logger.log(Level.INFO, "requestPath:" + requestPath);
		if (null != requestPath && requestPath.endsWith("/")) {
			return false;
		}
		// 拼接成classpath路径
		String path = new StringBuilder(Constants.PREFIX).append(requestPath).toString();
		// 在classpath下获取资源

		URL url = WebResourceUtil.class.getClassLoader().getResource(path);
		if (null == url) {
			return false;
		}
		// 获取文件信息，用于处理缓存
		String filePath = url.getFile();
		logger.log(Level.INFO, "filePath:" + filePath);
		if (!StringUtil.isNullOrEmpty(filePath)) {
			if (filePath.indexOf(Constants.FILE_FLAG) == 0) {
				filePath = filePath.substring(5);
			}
			if (!StringUtil.isNullOrEmpty(filePath)) {
				int idx = filePath.indexOf(Constants.IN_THE_JAR_FLAG);
				if (idx != -1) {
					filePath = filePath.substring(0, idx);
				}
			}

		}
		File file = new File(filePath);

		if (null == file || !file.exists() || !file.isFile()) {
			return false;
		}
		long lastModified = file.lastModified();

		// boolean isClearCache = false;
		//
		// HttpSession session = request.getSession();
		// if (null != session) {
		// Object isCache = session.getAttribute(Constants.CLEAR_CACHE_FLAG);
		// if (null != isCache) {
		// isClearCache = true;
		// }
		// }

		if (!checkHeaderCache(CACHE_DAYS, lastModified, request, response)) {
			return true;
		}

		// 发送资源
		InputStream resource = null;
		try {
			response.setStatus(HttpServletResponse.SC_OK);
			String suffix = null;
			String tmpFilePath = url.getFile();
			int lastIdx = tmpFilePath.lastIndexOf(SEPARATOR);
			if (lastIdx != -1) {
				suffix = tmpFilePath.substring(lastIdx + 1);
			}
			response.setContentType(ContentTypeUtil.getContentType(suffix));
			resource = url.openStream();
			StreamUtil.transferStream(resource, response.getOutputStream());
		} catch (IOException e) {
			StreamUtil.closeStream(resource);
			return false;
		} finally {
			StreamUtil.closeStream(resource);
		}
		return true;

	}

	/**
	 * 检查缓存
	 * 
	 * @param cacheSeconds
	 * @param lastModified
	 * @param request
	 * @param response
	 * @return
	 */
	public static boolean checkHeaderCache(long cacheSeconds, long lastModified, HttpServletRequest request, HttpServletResponse response) {

		// 缓存秒转换为毫秒
		long cacheMs = cacheSeconds * 1000;
		long header = request.getDateHeader(Constants.IF_MODIFIED_SINCE);
		long now = System.currentTimeMillis();
		if (header > 0 && cacheMs > 0) {
			if (lastModified > header) {
				// adddays = 0; // reset
				response.setStatus(HttpServletResponse.SC_OK);
				return true;
			}
			if (header + cacheMs > now) {
				// during the period happend modified
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return false;
			}
		}

		// if over expire data, see the Etags;
		// ETags if ETags no any modified
		String previousToken = request.getHeader(Constants.IF_NONE_MATCH);
		if (previousToken != null && previousToken.equals(Long.toString(lastModified))) {
			// not modified
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		// if th model has modified , setup the new modified date
		response.setHeader(Constants.E_TAG, Long.toString(lastModified));
		setRespHeaderCache(cacheSeconds, request, response);
		return true;

	}

	/**
	 * 设置缓存头
	 * 
	 * @param cacheSeconds
	 * @param request
	 * @param response
	 * @return
	 */
	private static boolean setRespHeaderCache(long cacheSeconds, HttpServletRequest request, HttpServletResponse response) {

		StringBuilder stringBuilder = new StringBuilder().append(Constants.MAX_AGE).append(cacheSeconds);
		String maxAgeDirective = stringBuilder.toString();
		response.setHeader(Constants.CACHE_CONTROL, maxAgeDirective);
		response.setStatus(HttpServletResponse.SC_OK);
		response.addDateHeader(Constants.LAST_MODIFIED, System.currentTimeMillis());
		response.addDateHeader(Constants.EXPIRES, System.currentTimeMillis() + cacheSeconds * 1000);
		return true;
	}
}
