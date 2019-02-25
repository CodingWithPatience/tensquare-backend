/**
 * 
 */
package com.zhihao.tensquare.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.entity.Admin;
import com.zhihao.tensquare.service.AdminService;
import com.zhihao.tensquare.service.LoginService;

/**
 * @author zzh
 * 2018年12月3日
 */
@RestController
@RequestMapping("admin")
@CrossOrigin
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private LoginService loginService;

	@PostMapping("")
	public Result<?> create(@RequestBody Admin admin) {
		adminService.create(admin);
		return new Result<>(true, StatusCode.OK, "添加成功", null);
	}
	
	@GetMapping("")
	public Result<?> getAll() {
		List<Admin> admins = adminService.findAll();
		return new Result<>(true, StatusCode.OK, "查询成功", admins);
	}

	@GetMapping("/info")
	public Result<?> getInfo() {
		Map<String, Object> info = adminService.getInfo();
		return new Result<>(true, StatusCode.OK, "查询成功", info);
	}

	@GetMapping("/{id}")
	public Result<?> getById(@PathVariable("id") String id) {
		Admin admin = adminService.findById(id);
		return new Result<>(true, StatusCode.OK, "查询成功", admin);
	}
	
	@PutMapping("/{id}")
	public Result<?> updateById(@RequestBody Admin admin, @PathVariable("id") String id) {
		admin.setId(id);
		adminService.update(admin);
		return new Result<>(true, StatusCode.OK, "更新成功", null);
	}
	
	@DeleteMapping("/{id}")
	public Result<?> deleteById(@PathVariable("id") String id) {
		adminService.deleteById(id);
		return new Result<>(true, StatusCode.OK, "删除成功", null);
	}

	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageable(@RequestBody Admin admin, @PathVariable("pageNum") int pageNum) {
		List<Admin> admins = adminService.searchPageable(admin, pageNum);
		return new Result<>(true, StatusCode.OK, "查询成功", admins);
	}
	
	@PostMapping("/login")
	public Result<?> login(@RequestBody Admin admin) {
		Map<String, Object> data = loginService.adminLogin(admin);
		if (data != null) {
			return new Result<>(true, StatusCode.OK, "登陆成功", data);
		}
		return new Result<>(false, StatusCode.LOGIN_ERROR, "登录名或密码错误", null);
	}

	@GetMapping("/logout")
	public Result<?> logout() {
		return new Result<>(true, StatusCode.OK, "退出登陆成功", null);
	}

}
