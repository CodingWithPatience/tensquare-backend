/**
 * 
 */
package com.zhihao.tensquare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.entity.Channel;
import com.zhihao.tensquare.service.ChannelService;

/**
 * @author zzh
 * 2018年11月20日
 */
@RestController
@CrossOrigin
@RequestMapping("channel")
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	
	@GetMapping("")
	public Result<?> listAll() {
		List<Channel> channels = channelService.findAll();
		String message = "返回所有的Channel对象";
		Result<List<Channel>> result = new Result<List<Channel>>(true, StatusCode.OK, message, channels);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable String id) {
		Channel channel = channelService.findById(id);
		String message = "返回id为"+id+"的Channel对象";
		Result<Channel> result = new Result<Channel>(true, StatusCode.OK, message, channel);
		return result;
	}
	
	@PostMapping()
	public Result<?> create(@RequestBody Channel channel) {
		channelService.create(channel);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功");
		return result;
	}
	
	@PostMapping("/search")
	public Result<?> search(@RequestBody Channel channel) {
		List<Channel> channels = channelService.search(channel);
		String message = "本次搜索返回"+channels.size()+"个结果";
		Result<?> result = new Result<>(true, StatusCode.OK, message, channels);
		return result;
	}
	
	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageabel(@RequestBody Channel channel,
			@PathVariable(value="pageNum") int pageNum) {
		List<Channel> channels = channelService.searchPageable(channel, pageNum);
		String message = "返回第"+pageNum+"页的搜索结果";
		Result<?> result = new Result<>(true, StatusCode.OK, message, channels);
		return result;
	}
	
	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody Channel channel) {
		channel.setId(id);
		channelService.update(channel);
		Result<?> result = new Result<>(true, StatusCode.OK, "更新成功");
		return result;
	}

	@DeleteMapping("")
	public Result<?> delete(@RequestBody Channel channel) {
		channelService.delete(channel);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功");
		return result;
	}

	@DeleteMapping("/{id}")
	public Result<?> deleteById(@PathVariable String id) {
		channelService.deleteById(id);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功");
		return result;
	}
	
}
