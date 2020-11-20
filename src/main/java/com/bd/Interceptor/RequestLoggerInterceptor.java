package com.bd.Interceptor;

import com.bd.utils.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录Action层用户操作监控日志定义
 */
@Slf4j
public class RequestLoggerInterceptor extends HandlerInterceptorAdapter {

	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

	/**
	 * Action之前执行,预处理回调方法，实现处理器的预处理（如登录检查），第三个参数为响应的处理器；
	 * 
	 * @return true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，
	 *         此时我们需要通过response来产生响应；
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 记录方法调用开始时间
		startTimeThreadLocal.set(System.currentTimeMillis());

		// 继续流程
		return true;
	}

	/**
	 * 生成视图之前执行,后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）
	 * 对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
	}

	/**
	 * afterCompletion：整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，
	 * 还可以进行一些资源清理，类似于try-catch-finally中的finally，
	 * 但仅调用处理器执行链中preHandle返回true的拦截器的afterCompletion。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		// 结束时间
		long endTime = System.currentTimeMillis();
		// 开始时间
		long callTime = startTimeThreadLocal.get();
		// 请求的URI
		String uri = request.getRequestURL().toString();
		// 用户IP
		String ip = HttpRequestUtils.getIpAddress(request);
		// 请求方式
		String methodType = request.getMethod();
		// 请求参数(只能获取到GET请求方式的)
		String parames = request.getQueryString();

		String jsonLog = this.getJson(callTime, ip, uri, endTime, methodType, parames);

		log.info(jsonLog);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	}

	private String getJson(long beginTime, String ip, String uri, long endTime, String methodType, String parames) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"beginTime\":\"");
		builder.append(beginTime);
		builder.append("\",\"endTime\":\"");
		builder.append(endTime);
		builder.append("\",\"ip\":\"");
		builder.append(ip);
		builder.append("\",\"methodType\":\"");
		builder.append(methodType);
		builder.append("\",\"uri\":\"");
		builder.append(uri);
		builder.append("\",\"parames\":\"");
		builder.append(parames);
		builder.append("\"}  ");
		return builder.toString();
	}

}
