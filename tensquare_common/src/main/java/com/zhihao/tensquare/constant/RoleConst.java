/**
 * 
 */
package com.zhihao.tensquare.constant;

/**
 * @author zzh
 * 2018年12月2日
 */
public class RoleConst {

	//用户分页大小
	public static final int USER_PAGE_SIZE = 10;
	//redis缓存key的前缀
	public static final String KEY_PREFFIX = "user_";
	//手机验证的三种状态；-1：验证码过期或系统出错，无法从缓存中获取验证码；0：验证码错误；1：验证通过
	public static final int CODE_EXPARE = -1;
	public static final int CODE_ERROR = 0;
	public static final int CODE_CORRECT = 1;
	
	//登陆角色
	public static final String ADMIN = "admin";
	public static final String NOMAL_USER = "user";
}
