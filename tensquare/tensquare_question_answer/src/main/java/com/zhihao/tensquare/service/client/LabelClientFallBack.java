/**
 * 
 */
package com.zhihao.tensquare.service.client;

import org.springframework.stereotype.Component;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;

import feign.hystrix.FallbackFactory;

/**
 * @author zzh
 * 2018年12月9日
 */
@Component
public class LabelClientFallBack implements FallbackFactory<LabelClientService> {

	@Override
	public LabelClientService create(Throwable cause) {
		return new LabelClientService() {
			
			@Override
			public Result<?> findLabelById(String id) {
				return new Result<>(false, StatusCode.REMOTE_ERROR, "服务调用失败", null);
			}
		};
	}

}
