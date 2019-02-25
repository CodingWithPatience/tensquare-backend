/**
 * 
 */
package com.zhihao.tensquare.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhihao.tensquare.util.IdWorker;

/**
 * @author zzh
 * 2018年11月19日
 */
@Configuration
public class CommonBeanConfig {

	@Bean
	public IdWorker idWorker() {
		return new IdWorker();
	}
}
