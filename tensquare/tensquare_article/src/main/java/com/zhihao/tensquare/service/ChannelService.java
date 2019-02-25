/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Channel;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface ChannelService {

	List<Channel> findAll();
	
	List<Channel> search(Channel channel);

	List<Channel> searchPageable(Channel channel, int pageNum);

	Channel findById(String id);

	void delete(Channel channel);
	
	void deleteById(String id);
	
	void create(Channel channel);
	
	void update(Channel channel);

}
