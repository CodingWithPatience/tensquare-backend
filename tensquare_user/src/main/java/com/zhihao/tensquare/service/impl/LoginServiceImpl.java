/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhihao.tensquare.constant.RoleConst;
import com.zhihao.tensquare.entity.Admin;
import com.zhihao.tensquare.entity.User;
import com.zhihao.tensquare.service.AdminService;
import com.zhihao.tensquare.service.LoginService;
import com.zhihao.tensquare.service.UserService;
import com.zhihao.tensquare.util.JwtUtil;

/**
 * @author zzh
 * 2018年12月3日
 */
@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtutil;
	
	@Override
	public Map<String, Object> userLogin(User user) {
		String mobile = user.getMobile();
		String password = user.getPassword();
		User userFromDB = userService.findByMobileAndPass(mobile, password);
		if (userFromDB==null) {
			return null;
		}
		//更新用户最后登陆时间
		userService.updateLoginTime(userFromDB.getId());
		//生成token
		String token = jwtutil.createJWT(userFromDB.getId(), userFromDB.getLoginName(), RoleConst.NOMAL_USER);
		Map<String, Object> data = new HashMap<>();
		data.put("token", token);
		data.put("roles", RoleConst.NOMAL_USER);
		data.put("nickName", userFromDB.getNickName());
		data.put("uid", userFromDB.getId());
		data.put("image", userFromDB.getImage());
		return data;
	}

	@Override
	public Map<String, Object> adminLogin(Admin admin) {
		String name = admin.getLoginName();
		String password = admin.getPassword();
		Admin adminFromDB = adminService.findByNameAndPass(name, password);
		if (adminFromDB==null) {
			return null;
		}
		//生成token
		String token = jwtutil.createJWT(adminFromDB.getId(), adminFromDB.getLoginName(), RoleConst.ADMIN);
		Map<String, Object> data = new HashMap<>();
		data.put("token", token);
		data.put("roles", RoleConst.ADMIN);
		return data;
	}

}
