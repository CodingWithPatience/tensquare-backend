/**
 * 
 */
package com.zhihao.tensquare.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;

/**
 * @author zzh
 * 2018年12月3日
 */
@ControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler(value={Exception.class})
	@ResponseBody
	public Result<?> baseHandler(Exception e) {
		e.printStackTrace();
		return new Result<>(false, StatusCode.ERROR, e.getMessage(), null);
	}
}
