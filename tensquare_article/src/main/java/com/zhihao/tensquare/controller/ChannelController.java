/**
 * 
 */
package com.zhihao.tensquare.controller;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.entity.Channel;
import com.zhihao.tensquare.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
		Result<List<Channel>> result = new Result<>(true, StatusCode.OK, "查询成功", channels);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable String id) {
		Channel channel = channelService.findById(id);
		Result<Channel> result = new Result<>(true, StatusCode.OK, "查询成功", channel);
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
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", channels);
		return result;
	}
	
	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageable(@RequestBody Channel channel,
			@PathVariable(value="pageNum") int pageNum) {
		List<Channel> channels = channelService.searchPageable(channel, pageNum);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", channels);
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
