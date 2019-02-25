/**
 * 
 */
package com.zhihao.tensquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author zzh
 * 2018年11月20日
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayWebApplication.class, args);
	}
}
