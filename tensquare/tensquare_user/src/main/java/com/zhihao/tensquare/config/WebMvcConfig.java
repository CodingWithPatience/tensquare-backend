/**
 * 
 */
package com.zhihao.tensquare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.zhihao.tensquare.interceptor.JwtInterceptor;

/**
 * @author zzh
 * 2018年12月3日
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	
	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("**/login/**");
	}
}
