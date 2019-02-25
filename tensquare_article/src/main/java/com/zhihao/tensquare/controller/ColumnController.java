/**
 * 
 */
package com.zhihao.tensquare.controller;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.entity.Article_Column;
import com.zhihao.tensquare.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zzh
 * 2018年11月26日
 */
@RestController
@CrossOrigin
@RequestMapping("column")
public class ColumnController {

	@Autowired
	private ColumnService columnService;
	
	@GetMapping("")
	public Result<?> listAll() {
		List<Article_Column> columns = columnService.findAll();
		Result<List<Article_Column>> result = new Result<>(true, StatusCode.OK, "查询成功", columns);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable String id) {
		Article_Column column = columnService.findById(id);
		Result<Article_Column> result = new Result<>(true, StatusCode.OK, "查询成功", column);
		return result;
	}
	
	@PostMapping()
	public Result<?> create(@RequestBody Article_Column column) {
		columnService.create(column);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功", null);
		return result;
	}
	
	@PostMapping("/search")
	public Result<?> search(@RequestBody Article_Column column) {
		List<Article_Column> columns = columnService.search(column);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", columns);
		return result;
	}
	
	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageable(@RequestBody Article_Column column,
			@PathVariable(value="pageNum") int pageNum) {
		List<Article_Column> columns = columnService.searchPageable(column, pageNum);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", columns);
		return result;
	}
	
	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody Article_Column column) {
		column.setId(id);
		boolean result = columnService.update(column);
		if (result) {
			return new Result<>(true, StatusCode.OK, "更新成功", null);
		}
		return new Result<>(false, StatusCode.ERROR, "更新失败", null);
	}

	@DeleteMapping("")
	public Result<?> delete(@RequestBody Article_Column column) {
		columnService.delete(column);
		Result<?> result = new Result<>(true, StatusCode.OK, "删除成功", null);
		return result;
	}

	@DeleteMapping("/{id}")
	public Result<?> deleteById(@PathVariable String id) {
		boolean result = columnService.deleteById(id);
		if (result) {
			return new Result<>(true, StatusCode.OK, "删除成功", null);
		}
		return new Result<>(false, StatusCode.ERROR, "删除失败", null);
	}
	
	@PutMapping("/examine/{id}")
	public Result<?> examine(@PathVariable(value="id") String id) {
		boolean result = columnService.approve(id);
		if (result) {
			return new Result<>(true, StatusCode.OK, "审核通过", null);
		}
		return new Result<>(false, StatusCode.ERROR, "审核不通过", null);
	}
	
}
