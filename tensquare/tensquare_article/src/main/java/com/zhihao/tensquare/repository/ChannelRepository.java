/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhihao.tensquare.entity.Channel;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface ChannelRepository extends JpaRepository<Channel, String>,
		JpaSpecificationExecutor<Channel> {
}
