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

import com.zhihao.tensquare.constant.EnterpriseConst;
import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.entity.Enterprise;
import com.zhihao.tensquare.service.EnterpriseService;

/**
 * @author zzh
 * 2018年11月20日
 */
@RestController
@CrossOrigin
@RequestMapping("enterprise")
public class EnterpriseController {

	@Autowired
	private EnterpriseService enterpriseService;
	
	@GetMapping("")
	public Result<?> listAll() {
		List<Enterprise> enterprises = enterpriseService.findAll();
		String message = "返回所有的Enterprise对象";
		Result<List<Enterprise>> result = new Result<List<Enterprise>>(true, StatusCode.OK, message, enterprises);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable String id) {
		Enterprise enterprise = enterpriseService.findById(id);
		String message = "返回id为"+id+"的Enterprise对象";
		Result<Enterprise> result = new Result<Enterprise>(true, StatusCode.OK, message, enterprise);
		return result;
	}
	
	@GetMapping("/hot")
	public Result<?> listHot() {
		List<Enterprise> enterprises = enterpriseService.findByIsHot(EnterpriseConst.HOT);
		String message = "返回"+enterprises.size()+"个热门的企业";
		Result<List<Enterprise>> result = new Result<List<Enterprise>>(true, StatusCode.OK, message, enterprises);
		return result;
	}
	
	@PostMapping()
	public Result<?> create(@RequestBody Enterprise enterprise) {
		enterpriseService.create(enterprise);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功");
		return result;
	}
	
	@PostMapping("/search")
	public Result<?> search(@RequestBody Enterprise enterprise) {
		List<Enterprise> enterprises = enterpriseService.search(enterprise);
		String message = "本次搜索返回"+enterprises.size()+"个结果";
		Result<?> result = new Result<>(true, StatusCode.OK, message, enterprises);
		return result;
	}
	
	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody Enterprise enterprise) {
		enterprise.setId(id);
		enterpriseService.update(enterprise);
		Result<?> result = new Result<>(true, StatusCode.OK, "更新成功");
		return result;
	}

	@DeleteMapping("")
	public Result<?> delete(@RequestBody Enterprise enterprise) {
		enterpriseService.delete(enterprise);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功");
		return result;
	}

	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable String id) {
		enterpriseService.deleteById(id);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功");
		return result;
	}
	
	@GetMapping("/total")
	public Result<?> total() {
		Long total = enterpriseService.total();
		String message = "Enterprise对象总数为"+total;
		Result<Long> result = new Result<Long>(true, StatusCode.OK, message, total);
		return result;
	}
}
