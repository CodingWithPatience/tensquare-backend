/**
 * 
 */
package com.zhihao.tensquare.listener;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zzh
 * 2018年12月2日
 */
@Component
@RabbitListener(queues="sms")
public class SMSListener {

	@RabbitHandler
	public void sendMessage(Map<String, String> message) {
		System.out.println("mobile:"+message.get("mobile"));
		System.out.println("code:"+message.get("code"));
	}
}
