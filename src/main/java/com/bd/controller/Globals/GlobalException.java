package com.bd.controller.Globals;

import com.bd.controller.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局统一异常处理类
 */
//控制器增强:ControllerAdvice已经被@Component修饰，则说明标记@ControllerAdvice会被扫描到容器中(看源码)。
@ControllerAdvice //(spring3.2带来的新特性)
@Slf4j
public class GlobalException {


	/**
	 * 统一处理程序运行时产生的异常到error日志文件中
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result errorExceptionHandler(Exception exception) {
		//将运行时异常的堆栈信息写入到error日志文件中
		log.error("服务器错误: {}", ExceptionUtils.getFullStackTrace(exception));
		return Result.fail(5000, "服务器出错，请联系接口工程师", exception.getMessage());
	}

	/**
	 * 统一处理接口参数传递问题(针对@RequestParam注解)到error日志文件中
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	public Result parameterExceptionHandler(MissingServletRequestParameterException ex) {
		//将运行时异常的堆栈信息写入到error日志文件中
		log.error("客户端错误: {}", ExceptionUtils.getFullStackTrace(ex));
		return Result.fail(4000, "客户端传递参数错误，请联系前端工程师", ex.getMessage());
	}

	/**
	 * 统一处理接口参数传递问题(针对@Valid注解)到error日志文件中
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public Result validExceptionHandler(MethodArgumentNotValidException ex) {
		//将运行时异常的堆栈信息写入到error日志文件中
		log.error("客户端错误: {}", ExceptionUtils.getFullStackTrace(ex));
		return Result.fail(4001, "客户端传递参数错误，请联系前端工程师",
				ex.getBindingResult().getFieldError().getDefaultMessage());
	}

}
