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
import com.zhihao.tensquare.entity.Reply;
import com.zhihao.tensquare.service.ReplyService;

/**
 * @author zzh
 * 2018年11月20日
 */
@RestController
@CrossOrigin
@RequestMapping("reply")
public class ReplyController {

	@Autowired
	private ReplyService replyService;

	@Autowired
	private HttpServletRequest request;
	
	@GetMapping("/problem/{problemId}/{pageNum}")
	public Result<?> listAll(@PathVariable(value="problemId") String problemId,
			@PathVariable(value="pageNum") int pageNum) {
		List<Reply> replies = replyService.findByProblemId(problemId, pageNum);
		Result<List<Reply>> result = new Result<>(true, StatusCode.OK, "查询成功", replies);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<?> listById(@PathVariable String id) {
		Reply reply = replyService.findById(id);
		Result<Reply> result = new Result<>(true, StatusCode.OK, "查询成功", reply);
		return result;
	}
	
	@GetMapping("/{userId}/{pageNum}")
	public Result<?> listReplyFromUser(@PathVariable(value="userId") String userId,
			@PathVariable(value="pageNum") int pageNum) {
		List<Reply> replies = replyService.findByUserId(userId, pageNum);
		Result<List<Reply>> result = new Result<>(true, StatusCode.OK, "查询成功", replies);
		return result;
	}
	
	@PostMapping()
	public Result<?> create(@RequestBody Reply reply) {
		reply.setUserId((String)request.getAttribute("userId"));
		replyService.create(reply);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功", null);
		return result;
	}
	
	@PostMapping("/search/{pageNum}")
	public Result<?> search(@RequestBody Reply reply,
			@PathVariable(value="pageNum") int pageNum) {
		List<Reply> replies = replyService.search(reply, pageNum);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", replies);
		return result;
	}
	
	@PutMapping("/{id}")
	public Result<?> update(@PathVariable String id, @RequestBody Reply reply) {
		reply.setId(id);
		boolean flag = replyService.update(reply);
		if (flag) {
			return new Result<>(true, StatusCode.OK, "更新成功", null);
		}
		return new Result<>(false, StatusCode.ERROR, "更新失败", null);
	}

	@DeleteMapping("/{id}")
	public Result<?> delete(@PathVariable String id) {
		boolean result = replyService.deleteById(id);
		if (result) {
			return new Result<>(true, StatusCode.OK, "删除成功", null);
		}
		return new Result<>(false, StatusCode.ERROR, "删除失败", null);
	}
	
	@GetMapping("/{problemId}/count")
	public Result<?> replyNumberOfProblem(@PathVariable(value="problemId") String problemId) {
		Long total = replyService.replyNumberForProblem(problemId);
		Result<Long> result = new Result<>(true, StatusCode.OK, "查询成功", total);
		return result;
	}
	
	@GetMapping("/{userId}/total")
	public Result<?> replyNumberOfUser(@PathVariable(value="userId") String userId) {
		Long total = replyService.replyNumberForUser(userId);
		Result<Long> result = new Result<>(true, StatusCode.OK, "查询成功", total);
		return result;
	}
	
	@GetMapping("/{userId}/{problemId}/total")
	public Result<?> replyNumberOfUserForProblem(@PathVariable(value="userId") String userId,
			@PathVariable(value="problemId") String problemId) {
		Long total = replyService.replyNumberForUserAndProblem(problemId, userId);
		Result<Long> result = new Result<>(true, StatusCode.OK, "查询成功", total);
		return result;
	}
}
