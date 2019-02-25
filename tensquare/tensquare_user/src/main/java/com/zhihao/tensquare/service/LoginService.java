/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.Map;

import com.zhihao.tensquare.entity.Admin;
import com.zhihao.tensquare.entity.User;

/**
 * @author zzh
 * 2018年12月3日
 */
public interface LoginService {
	
	Map<String, Object> userLogin(User user);

	Map<String, Object> adminLogin(Admin admin);
}
