/**
 * 
 */
package com.zhihao.tensquare.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zhihao.tensquare.dto.Result;

/**
 * @author zzh
 * 2018年12月9日
 */
@FeignClient(value="tensquare-base", fallbackFactory=LabelClientFallBack.class)
public interface LabelClientService {

	@GetMapping("label/{id}")
	Result<?> findLabelById(@PathVariable("id") String id);
}
