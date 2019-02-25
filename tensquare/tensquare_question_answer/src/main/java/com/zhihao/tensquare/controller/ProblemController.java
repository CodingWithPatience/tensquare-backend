/**
 *
 */
package com.zhihao.tensquare.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.zhihao.tensquare.entity.Problem;
import com.zhihao.tensquare.service.ProblemService;
import com.zhihao.tensquare.service.client.LabelClientService;

/**
 * @author zzh
 * 2018年11月20日
 */
@RestController
@CrossOrigin
@RequestMapping("problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private LabelClientService labelClientService;

	@Autowired
	private HttpServletRequest request;

	@GetMapping("")
	public Result<?> listAll() {
		List<Problem> problems = problemService.findAll();
		Result<List<Problem>> result = new Result<>(true, StatusCode.OK, "查询成功", problems);
		return result;
	}

	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable(value="id") String id) {
		Problem problem = problemService.findById(id);
		Result<Problem> result = new Result<>(true, StatusCode.OK, "查询成功", problem);
		return result;
	}

	@GetMapping("/label/{labelId}")
	public Result<?> findLabelById(@PathVariable(value="labelId") String labelId) {
		Result<?> result = labelClientService.findLabelById(labelId);
		return result;
	}

	@GetMapping("/{labelId}/{pageNum}")
	public Result<?> listAllByLabelId(@PathVariable(value="labelId") String labelId,
									  @PathVariable(value="pageNum") int pageNum) {
		List<Problem> problems = problemService.findAllByLabelId(labelId, pageNum);
		Result<List<Problem>> result = new Result<>(true, StatusCode.OK, "查询成功", problems);
		return result;
	}

	@GetMapping("/hot/{labelId}/{pageNum}")
	public Result<?> listHotProblem(@PathVariable(value="labelId") String labelId,
									@PathVariable(value="pageNum", required=false) int pageNum) {
		List<Problem> problems = problemService.findHotProblem(labelId, pageNum);
		Result<List<Problem>> result = new Result<>(true, StatusCode.OK, "查询成功", problems);
		return result;
	}

	@GetMapping("/newest/{labelId}/{pageNum}")
	public Result<?> listNewestProblem(@PathVariable(value="labelId") String labelId,
									   @PathVariable(value="pageNum",required=false) int pageNum) {
		List<Problem> problems = problemService.findNewestProblem(labelId, pageNum);
		Result<List<Problem>> result = new Result<>(true, StatusCode.OK, "查询成功", problems);
		return result;
	}

	@GetMapping("/wait/{labelId}/{pageNum}")
	public Result<?> listWaitToAnswer(@PathVariable(value="labelId") String labelId,
									  @PathVariable(value="pageNum",required=false) int pageNum) {
		List<Problem> problems = problemService.findWaitToAnswer(labelId, pageNum);
		Result<List<Problem>> result = new Result<>(true, StatusCode.OK, "查询成功", problems);
		return result;
	}

	@PostMapping()
	public Result<?> create(@RequestBody Problem problem) {
		problem.setUserId((String)request.getAttribute("userId"));
		problemService.create(problem);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功", null);
		return result;
	}

	@PostMapping("/search")
	public Result<?> search(@RequestBody Problem problem) {
		List<Problem> problems = problemService.search(problem);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", problems);
		return result;
	}

	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageable(@RequestBody Problem problem,
									@PathVariable(value="pageNum",required=false) int pageNum) {
		List<Problem> problems = problemService.searchPageable(problem, pageNum);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", problems);
		return result;
	}

	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody Problem problem) {
		problem.setId(id);
		boolean flag = problemService.update(problem);
		if (flag) {
			return new Result<>(true, StatusCode.OK, "更新成功", null);
		}
		return new Result<>(false, StatusCode.ERROR, "更新失败", null);
	}

	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable String id) {
		boolean result = problemService.deleteById(id);
		if (result) {
			return new Result<>(true, StatusCode.OK, "删除成功", null);
		}
		return new Result<>(false, StatusCode.ERROR, "删除失败", null);
	}

	@GetMapping("/total")
	public Result<?> total() {
		Long total = problemService.total();
		Result<Long> result = new Result<>(true, StatusCode.OK, "查询成功", total);
		return result;
	}

	@GetMapping("/{labelId}/total")
	public Result<?> numberOfLabel(@PathVariable(value="labelId") String labelId) {
		Long total = problemService.problemNumberForLabel(labelId);
		Result<Long> result = new Result<>(true, StatusCode.OK, "查询成功", total);
		return result;
	}
}
