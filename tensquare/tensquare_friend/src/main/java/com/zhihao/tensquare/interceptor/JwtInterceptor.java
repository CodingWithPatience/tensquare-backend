/**
 * 
 */
package com.zhihao.tensquare.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zhihao.tensquare.constant.RoleConst;
import com.zhihao.tensquare.util.JwtUtil;

import io.jsonwebtoken.Claims;

/**
 * @author zzh
 * 2018年12月3日
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JwtUtil jwtUtil;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		String header = request.getHeader("Authorization");
		if (header!=null && !"".equals(header)) {
			if (header.startsWith("mytoken_")) {
				final String token = header.substring(8);
				try {
					Claims claims = jwtUtil.parseJWT(token);
					if (claims!=null && RoleConst.ADMIN.equals(claims.get("roles"))) {
						request.setAttribute("admin_claims", claims);
					}
					if (claims!=null && RoleConst.NOMAL_USER.equals(claims.get("roles"))) {
						request.setAttribute("user_claims", claims);
					}
				} catch (Exception e) {
					throw new RuntimeException("token解析出错！");
				}
			}
		}
		return true;
	}
}
