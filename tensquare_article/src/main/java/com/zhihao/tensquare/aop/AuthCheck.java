/**
 * 
 */
package com.zhihao.tensquare.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

/**
 * @author zzh
 * 2018年12月6日
 */
@Aspect
@Component
public class AuthCheck {

	private final Logger logger = LogManager.getLogger(AuthCheck.class);
	
	@Autowired
	private HttpServletRequest request;

	@Pointcut("(execution(* com.zhihao.tensquare.service..*ServiceImpl.update*(..))) "
			+ "|| (execution(* com.zhihao.tensquare.service..*ServiceImpl.delete*(..))) "
			+ "|| (execution(* com.zhihao.tensquare.service..*ServiceImpl.create*(..)))")
	public void checkAuth() {
	}
	
	@Pointcut("execution(* com.zhihao.tensquare.service..*ServiceImpl.approve(String))")
	public void checkAdmin() {
	}
	
	@Before("checkAuth()")
	public void beforeAction() {
		logger.info("进入切面，token验证，验证该用户是否具备操作权限");
        Claims claims = (Claims) request.getAttribute("user_claims");
		if (claims == null) {
			logger.warn("token验证不通过，该用户不具备操作权限");
			throw new RuntimeException("权限不足");
		}
		logger.info("token验证通过，该用户具备下一步操作的权限");
	}
	
	@Before("checkAdmin()")
	public void beforeApprove() {
		logger.info("进入切面，token验证，验证是否具有管理员权限");
        Claims claims = (Claims) request.getAttribute("admin_claims");
		if (claims == null) {
			logger.warn("token验证不通过，不具备管理员权限");
			throw new RuntimeException("权限不足");
		}
		logger.info("token验证通过，用户具有管理员权限，可以进行审核操作");
	}
}
