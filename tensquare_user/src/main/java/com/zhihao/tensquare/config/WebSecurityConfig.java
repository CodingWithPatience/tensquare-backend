/**
 * 
 */
package com.zhihao.tensquare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全注解类
 * @author zzh
 * 2018年12月3日
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.authorizeRequests()
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated()
				.and().csrf().disable();
	}
}
