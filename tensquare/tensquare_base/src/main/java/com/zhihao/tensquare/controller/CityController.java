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
import com.zhihao.tensquare.entity.City;
import com.zhihao.tensquare.service.CityService;

/**
 * @author zzh
 * 2018年11月20日
 */
@CrossOrigin
@RestController
@RequestMapping("city")
public class CityController {

	@Autowired
	private CityService cityService;
	
	@GetMapping("")
	public Result<?> listAll() {
		List<City> citys = cityService.findAll();
		Result<List<City>> result = new Result<List<City>>(true, StatusCode.OK, "查询成功", citys);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable String id) {
		City city = cityService.findById(id);
		Result<City> result = new Result<City>(true, StatusCode.OK, "查询成功", city);
		return result;
	}
	
	@PostMapping()
	public Result<?> create(@RequestBody City city) {
		cityService.create(city);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功", null);
		return result;
	}
	
	@PostMapping("/search")
	public Result<?> search(@RequestBody City city) {
		List<City> cities = cityService.search(city);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", cities);
		return result;
	}
	
	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody City city) {
		city.setId(id);
		cityService.update(city);
		Result<?> result = new Result<>(true, StatusCode.OK, "更新成功", null);
		return result;
	}

	@DeleteMapping("")
	public Result<?> delete(@RequestBody City city) {
		cityService.delete(city);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功", null);
		return result;
	}

	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable String id) {
		cityService.deleteById(id);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功", null);
		return result;
	}
	
	@GetMapping("/total")
	public Result<?> total() {
		Long total = cityService.total();
		Result<Long> result = new Result<Long>(true, StatusCode.OK, "查询成功", total);
		return result;
	}
}
