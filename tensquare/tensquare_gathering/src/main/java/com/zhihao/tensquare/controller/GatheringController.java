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
import com.zhihao.tensquare.entity.Gathering;
import com.zhihao.tensquare.service.GatheringService;

/**
 * @author zzh
 * 2018年11月20日
 */
@RestController
@CrossOrigin
@RequestMapping("gathering")
public class GatheringController {

	@Autowired
	private GatheringService gatheringService;
	
	@GetMapping("")
	public Result<?> listAll() {
		List<Gathering> gatherings = gatheringService.findAll();
		String message = "返回所有的Gathering对象";
		Result<List<Gathering>> result = new Result<List<Gathering>>(true, StatusCode.OK, message, gatherings);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable String id) {
		Gathering gathering = gatheringService.findById(id);
		String message = "返回id为"+id+"的Gathering对象";
		Result<Gathering> result = new Result<Gathering>(true, StatusCode.OK, message, gathering);
		return result;
	}
	
	@GetMapping("/city/{cityId}/{pageNum}")
	public Result<?> listByCity(@PathVariable("cityId") String cityId, @PathVariable("pageNum") int pageNum) {
		List<Gathering> gatherings = gatheringService.findByCityId(cityId, pageNum);
		String message = "返回第"+pageNum+"页city属性为"+cityId+"的Gathering对象";
		Result<List<Gathering>> result = new Result<List<Gathering>>(true, StatusCode.OK, message, gatherings);
		return result;
	}
	
	@PostMapping()
	public Result<?> create(@RequestBody Gathering gathering) {
		gatheringService.create(gathering);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功");
		return result;
	}
	
	@PostMapping("/search")
	public Result<?> search(@RequestBody Gathering gathering) {
		List<Gathering> gatherings = gatheringService.search(gathering);
		String message = "本次搜索返回"+gatherings.size()+"个结果";
		Result<?> result = new Result<>(true, StatusCode.OK, message, gatherings);
		return result;
	}
	
	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageable(@RequestBody Gathering gathering, @PathVariable("pageNum") int pageNum) {
		List<Gathering> gatherings = gatheringService.searchPageable(gathering, pageNum);
		String message = "返回第"+pageNum+"页Gathering对象";
		Result<?> result = new Result<>(true, StatusCode.OK, message, gatherings);
		return result;
	}
	
	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody Gathering gathering) {
		gathering.setId(id);
		gatheringService.update(gathering);
		Result<?> result = new Result<>(true, StatusCode.OK, "更新成功");
		return result;
	}

	@DeleteMapping("")
	public Result<?> delete(@RequestBody Gathering gathering) {
		gatheringService.delete(gathering);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功");
		return result;
	}

	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable String id) {
		gatheringService.deleteById(id);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功");
		return result;
	}
	
}
