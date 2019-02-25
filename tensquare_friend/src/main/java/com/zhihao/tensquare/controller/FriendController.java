/**
 * 
 */
package com.zhihao.tensquare.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.zhihao.tensquare.constant.FriendConst;
import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.service.FriendService;
import com.zhihao.tensquare.service.client.UserClientService;

import io.jsonwebtoken.Claims;

/**
 * @author zzh
 * 2018年12月4日
 */
@RestController
@RequestMapping("friend")
@CrossOrigin
public class FriendController {

	@Autowired
	private FriendService friendService;
	
	@Autowired
	private UserClientService userClientService;

	@Autowired
	private HttpServletRequest request;
	
	@PutMapping("/like/{friendId}/{type}")
	public Result<?> friend(@PathVariable("friendId") String friendId,
			@PathVariable("type") int type) {
		Claims claims = (Claims) request.getAttribute("user_claims");
		if (claims == null) {
			return new Result<>(false, StatusCode.ACCESS_ERROR, "权限不足", null);
		}
		Result<?> result = null;
		String userId = claims.getId();
		if (type == FriendConst.FRIEND) {
			friendService.friend(userId, friendId);
			//调用tensquare_user微服务，更新用户的关注数和好友的粉丝数
			result = userClientService.follow(userId, friendId);
		}
		else if (type == FriendConst.NO_FRIEND) {
			friendService.noFriend(userId, friendId);
			return new Result<>(true, StatusCode.OK, "加入到非好友列表成功", null);
		}
		else {
			return new Result<>(false, StatusCode.ERROR, "参数有误", null);
		}
		return result;
	}
	
	@DeleteMapping("/{friendId}")
	public Result<?> noFriend(@PathVariable("friendId") String friendId) {
		Claims claims = (Claims) request.getAttribute("user_claims");
		if (claims == null) {
			return new Result<>(false, StatusCode.ACCESS_ERROR, "权限不足", null);
		}
		String userId = claims.getId();
		friendService.deleteByUserIdAndFriendId(userId, friendId);
		//调用tensquare_user微服务，更新用户的关注数和好友的粉丝数
		Result<?> result = userClientService.unfollow(userId, friendId);
		return result;
	}
}
