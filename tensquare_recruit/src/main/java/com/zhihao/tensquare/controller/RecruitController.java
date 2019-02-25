/**
 * 
 */
package com.zhihao.tensquare.controller;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.entity.Recruit;
import com.zhihao.tensquare.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zzh
 * 2018年11月20日
 */
@RestController
@CrossOrigin
@RequestMapping("recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService;
	
	@GetMapping("")
	public Result<?> listAll() {
		List<Recruit> recruits = recruitService.findAll();
		String message = "查询成功";
		Result<List<Recruit>> result = new Result<>(true, StatusCode.OK, message, recruits);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable String id) {
		Recruit recruit = recruitService.findById(id);
		String message = "查询成功";
		Result<Recruit> result = new Result<>(true, StatusCode.OK, message, recruit);
		return result;
	}
	
	@GetMapping("/recommend")
	public Result<?> listRecommend() {
		List<Recruit> recruits = recruitService.findRecommendJob();
		String message = "查询成功";
		Result<List<Recruit>> result = new Result<>(true, StatusCode.OK, message, recruits);
		return result;
	}
	
	@GetMapping("/newest")
	public Result<?> listNewest() {
		List<Recruit> recruits = recruitService.findNewestJob();
		String message = "查询成功";
		Result<List<Recruit>> result = new Result<>(true, StatusCode.OK, message, recruits);
		return result;
	}
	
	@PostMapping()
	public Result<?> create(@RequestBody Recruit recruit) {
		recruitService.create(recruit);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功");
		return result;
	}
	
	@PostMapping("/search")
	public Result<?> search(@RequestBody Recruit recruit) {
		List<Recruit> recruits = recruitService.search(recruit);
		String message = "查询成功";
		Result<?> result = new Result<>(true, StatusCode.OK, message, recruits);
		return result;
	}

	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageable(@RequestBody Recruit recruit, @PathVariable int pageNum) {
		List<Recruit> recruits = recruitService.searchPageable(recruit, pageNum);
		String message = "查询成功";
		Result<?> result = new Result<>(true, StatusCode.OK, message, recruits);
		return result;
	}
	
	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody Recruit recruit) {
		recruit.setId(id);
		recruitService.update(recruit);
		Result<?> result = new Result<>(true, StatusCode.OK, "更新成功");
		return result;
	}

	@DeleteMapping("")
	public Result<?> delete(@RequestBody Recruit recruit) {
		recruitService.delete(recruit);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功");
		return result;
	}

	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable String id) {
		recruitService.deleteById(id);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功");
		return result;
	}
	
	@GetMapping("/total")
	public Result<?> total() {
		Long total = recruitService.total();
		String message = "查询成功";
		Result<Long> result = new Result<>(true, StatusCode.OK, message, total);
		return result;
	}
}
