/**
 * 
 */
package com.zhihao.tensquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zzh
 * 2018年11月20日
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class QuestionAnswerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionAnswerApplication.class, args);
	}
}
