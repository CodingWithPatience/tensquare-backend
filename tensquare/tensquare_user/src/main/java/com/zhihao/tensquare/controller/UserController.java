/**
 * 
 */
package com.zhihao.tensquare.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.constant.RoleConst;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.entity.User;
import com.zhihao.tensquare.service.LoginService;
import com.zhihao.tensquare.service.RegisterService;
import com.zhihao.tensquare.service.UserService;

/**
 * @author zzh
 * 2018年12月2日
 */
@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("")
	public Result<?> getAll() {
		List<User> users = userService.findAll();
		return new Result<>(true, StatusCode.OK, "查询成功", users);
	}

	@GetMapping("/{id}")
	public Result<?> getById(@PathVariable("id") String id) {
		User user = userService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", user);
	}

	@PostMapping("")
	public Result<?> create(@RequestBody User user) {
		userService.create(user);
		return new Result<>(true, StatusCode.OK, "添加成功", null);
	}

	@PutMapping("/{id}")
	public Result<?> updateById(@RequestBody User user, @PathVariable("id") String id) {
		user.setId(id);
		userService.update(user);
		return new Result<>(true, StatusCode.OK, "更新成功", null);
	}

	@DeleteMapping("/{id}")
	public Result<?> deleteById(@PathVariable("id") String id) {
		try {
			userService.deleteById(id);
			return new Result<>(true, StatusCode.OK, "删除成功", null);
		} catch (RuntimeException e) {
			return new Result<>(false, StatusCode.ACCESS_ERROR, e.getMessage(), null);
		}
	}

	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageable(@RequestBody User user, @PathVariable("pageNum") int pageNum) {
		List<User> users = userService.searchPageable(user, pageNum);
		return new Result<>(true, StatusCode.OK, "查询成功", users);
	}

	@PostMapping("/sendmessage")
	public Result<?> sendMessage(@RequestBody User user) {
		String mobile = user.getMobile();
		String code = registerService.sendMessage(mobile);
		return new Result<>(true, StatusCode.OK, "发送成功", code);
	}

	@PostMapping("/register/{code}")
	public Result<?> register(@RequestBody User user, @PathVariable("code") String code) {
		Result<?> result = null;
		String mobile = user.getMobile();
		int status = registerService.validateCode(mobile, code);
		switch (status) {
		case RoleConst.CODE_EXPARE:
			result = new Result<>(false, StatusCode.ERROR, "验证码过期", null);
			break;
		case RoleConst.CODE_ERROR:
			result = new Result<>(false, StatusCode.ERROR, "请输入正确的验证码", null);
			break;
		case RoleConst.CODE_CORRECT:
			userService.create(user);
			result = new Result<>(true, StatusCode.OK, "注册成功", null);
			break;
		default:
			result = new Result<>(false, StatusCode.ERROR, "系统错误", null);
			break;
		}	
		return result;
	}
	
	@PostMapping("/login")
	public Result<?> login(@RequestBody User user) {
		Map<String, Object> data = loginService.userLogin(user);
		if (data != null) {
			return new Result<>(true, StatusCode.OK, "登陆成功", data);
		}
		return new Result<>(false, StatusCode.LOGIN_ERROR, "用户名或密码错误", null);
	}
	
	@PutMapping("follow/{userId}/{friendId}")
	public Result<?> follow(@PathVariable("userId") String userId,
			@PathVariable("friendId") String friendId) {
		userService.follow(userId, friendId);
		return new Result<>(true, StatusCode.OK, "关注成功", null);
	}

	@DeleteMapping("follow/{userId}/{friendId}")
	public Result<?> unfollow(@PathVariable("userId") String userId,
			@PathVariable("friendId") String friendId) {
		userService.unfollow(userId, friendId);
		return new Result<>(true, StatusCode.OK, "取消关注成功", null);
	}
}
