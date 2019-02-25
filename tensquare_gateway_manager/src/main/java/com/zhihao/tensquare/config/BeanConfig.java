/**
 * 
 */
package com.zhihao.tensquare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhihao.tensquare.util.JwtUtil;

/**
 * @author zzh
 * 2018年12月3日
 */
@Configuration
public class BeanConfig {

	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}
}
