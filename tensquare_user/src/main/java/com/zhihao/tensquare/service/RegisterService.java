/**
 * 
 */
package com.zhihao.tensquare.service;

/**
 * @author zzh
 * 2018年12月2日
 */
public interface RegisterService {
	
	void sendMessage(String mobile);

	int validateCode(String mobile, String code);
}
