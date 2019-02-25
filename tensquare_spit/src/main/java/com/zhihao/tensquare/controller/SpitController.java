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
import com.zhihao.tensquare.entity.Spit;
import com.zhihao.tensquare.service.SpitService;

/**
 * @author zzh
 * 2018年11月28日
 */
@RestController
@CrossOrigin
@RequestMapping("spit")
public class SpitController {

	@Autowired
	private SpitService spitService;
	
	@GetMapping("")
	public Result<List<Spit>> listAll() {
		List<Spit> spits = spitService.findAll();
		String message = "查询成功";
		Result<List<Spit>> result = new Result<>(true, StatusCode.OK, message, spits);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable String id) {
		Spit spit = spitService.findById(id);
		String message = "查询成功";
		Result<Spit> result = new Result<>(true, StatusCode.OK, message, spit);
		return result;
	}
	
	@GetMapping("/comment/{parentId}/{pageNum}")
	public Result<?> listByCity(@PathVariable("parentId") String parentId, @PathVariable("pageNum") int pageNum) {
		List<Spit> spits = spitService.findByParentId(parentId, pageNum);
		String message = "查询成功";
		Result<List<Spit>> result = new Result<>(true, StatusCode.OK, message, spits);
		return result;
	}
	
	@PostMapping()
	public Result<?> create(@RequestBody Spit spit) {
		spitService.create(spit);
		String message = "添加成功";
		Result<?> result = new Result<>(true, StatusCode.OK, message, null);
		return result;
	}
	
	@PostMapping("/search")
	public Result<?> search(@RequestBody Spit spit) {
		List<Spit> spits = spitService.search(spit);
		String message = "查询成功";
		Result<?> result = new Result<>(true, StatusCode.OK, message, spits);
		return result;
	}
	
	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageable(@RequestBody Spit spit, @PathVariable("pageNum") int pageNum) {
		List<Spit> spits = spitService.searchPageable(spit, pageNum);
		String message = "查询成功";
		Result<?> result = new Result<>(true, StatusCode.OK, message, spits);
		return result;
	}
	
	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody Spit spit) {
		spit.set_id(id);
		spitService.update(spit);
		String message = "更新成功";
		Result<?> result = new Result<>(true, StatusCode.OK, message, null);
		return result;
	}

	@PutMapping("/thumbUp/{id}/{userId}")
	public Result<?> like(@PathVariable(value = "id") String id,
						  @PathVariable(value = "userId") String userId) {
		String message;
		if (spitService.like(id, userId)) 
			message = "点赞成功";
		else 
			message = "重复点赞";
		Result<?> result = new Result<>(true, StatusCode.OK, message, null);
		return result;
	}

	
	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable String id) {
		spitService.deleteById(id);
		String message = "删除成功";
		Result<?> result = new Result<>(true, StatusCode.OK, message, null);
		return result;
	}
}
