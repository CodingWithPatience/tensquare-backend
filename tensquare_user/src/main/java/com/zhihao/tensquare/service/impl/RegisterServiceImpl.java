/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zhihao.tensquare.constant.RoleConst;
import com.zhihao.tensquare.service.RegisterService;

/**
 * @author zzh
 * 2018年12月2日
 */
@Service
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void sendMessage(String mobile) {
		//生产6位的随机数字符串验证码
		String code = RandomStringUtils.randomNumeric(6);
		//将验证码存储在redis中，有效期为1小时
		redisTemplate.opsForValue().set(RoleConst.KEY_PREFFIX+mobile, code, 1, TimeUnit.HOURS);
		//向用户发短信
		Map<String, String> message = new HashMap<>();
		message.put("mobile", mobile);
		message.put("code", code);
		rabbitTemplate.convertAndSend("sms", message);
		System.out.println("验证码为："+code);
	}

	@Override
	public int validateCode(String mobile, String code) {
		String codeFromCache = (String) redisTemplate.opsForValue().get(RoleConst.KEY_PREFFIX+mobile);
		if (codeFromCache.isEmpty() || codeFromCache==null) {
			return RoleConst.CODE_EXPARE;        //验证码过期
		}
		if (!code.equals(codeFromCache)) {
			return RoleConst.CODE_ERROR;         //验证码错误
		}
		return RoleConst.CODE_CORRECT;           //验证通过
	}

}
