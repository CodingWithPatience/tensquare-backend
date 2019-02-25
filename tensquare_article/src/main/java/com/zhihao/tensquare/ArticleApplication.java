/**
 * 
 */
package com.zhihao.tensquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author zzh
 * 2018年11月20日
 */
@SpringBootApplication
@EnableEurekaClient
public class ArticleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleApplication.class, args);
	}
}
