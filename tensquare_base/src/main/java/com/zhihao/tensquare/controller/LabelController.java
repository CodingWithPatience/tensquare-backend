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
import com.zhihao.tensquare.entity.Label;
import com.zhihao.tensquare.service.LabelService;

/**
 * @author zzh
 * 2018年11月19日
 */
@CrossOrigin
@RestController
@RequestMapping("label")
public class LabelController {

	@Autowired
	private LabelService labelService;
	
	@GetMapping("")
	public Result<List<Label>> listAll() {
		List<Label> labels = labelService.findAll();
		Result<List<Label>> result = new Result<>(true, StatusCode.OK, "查询成功", labels);
		return result;
	}

	@GetMapping("/toplist")
	public Result<List<Label>> topList() {
		List<Label> labels = labelService.topList();
		Result<List<Label>> result = new Result<>(true, StatusCode.OK, "查询成功", labels);
		return result;
	}

	@GetMapping("/{id}")
	public Result<Label> listById(@PathVariable String id) {
		Label label = labelService.findById(id);
		Result<Label> result = new Result<>(true, StatusCode.OK, "查询成功", label);
		return result;
	}
	
	@PostMapping()
	public Result<?> create(@RequestBody Label label) {
		labelService.create(label);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功", null);
		return result;
	}
	
	@PostMapping("/search")
	public Result<?> search(@RequestBody Label label) {
		List<Label> labels = labelService.search(label);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", labels);
		return result;
	}
	
	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody Label label) {
		label.setId(id);
		System.out.println(label);
		labelService.update(label);
		Result<?> result = new Result<>(true, StatusCode.OK, "更新成功", null);
		return result;
	}

	@DeleteMapping("")
	public Result<?> delete(@RequestBody Label label) {
		labelService.delete(label);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功", null);
		return result;
	}

	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable String id) {
		labelService.deleteById(id);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功", null);
		return result;
	}
	
	@GetMapping("/total")
	public Result<?> total() {
		Long total = labelService.total();
		Result<Long> result = new Result<>(true, StatusCode.OK, "查询成功", total);
		return result;
	}
}
