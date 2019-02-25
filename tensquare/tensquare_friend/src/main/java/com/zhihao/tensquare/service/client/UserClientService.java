/**
 * 
 */
package com.zhihao.tensquare.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.zhihao.tensquare.dto.Result;

/**
 * @author zzh
 * 2018年12月5日
 */
@FeignClient(name = "tensquare-user")
public interface UserClientService {

	@PutMapping("user/follow/{userId}/{friendId}")
	public Result<?> follow(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId);
	
	@DeleteMapping("user/follow/{userId}/{friendId}")
	public Result<?> unfollow(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId);
}
