/**
 * 
 */
package com.zhihao.tensquare.controller;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.entity.Article;
import com.zhihao.tensquare.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zzh
 * 2018年11月20日
 */
@RestController
@CrossOrigin
@RequestMapping("article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@GetMapping(value="/{id}/like")
	public Result<?> addLike(@PathVariable(value="id") String id) {
		articleService.updateLike(id);
		Result<?> result = new Result<>(true, StatusCode.OK, "点赞成功", null);
		return result;
	}
	
	@PutMapping(value="/{id}/examine")
	public Result<?> examine(@PathVariable(value="id") String id) {
		boolean result = articleService.approve(id);
		if (result) {
			return new Result<>(true, StatusCode.OK, "审核通过", null);
		}
		return new Result<>(false, StatusCode.ERROR, "审核不通过", null);
	}
	
	@PostMapping("")
	public Result<?> createArticle(@RequestBody Article article) {
		articleService.create(article);
		Result<?> result = new Result<>(true, StatusCode.OK, "添加成功", null);
		return result;
	}

	@GetMapping("")
	public Result<?> articleList() {
		List<Article> articles = articleService.listAll();
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", articles);
		return result;
	}

	@GetMapping("/{id}")
	public Result<?> getArticleById(@PathVariable(value="id") String id) {
		Article article = articleService.getById(id);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", article);
		return result;
	}

	@PutMapping("/{id}")
	public Result<?> updateArticle(@RequestBody Article article, @PathVariable(value="id") String id) {
		article.setId(id);
		boolean flag = articleService.update(article);
		if (flag) {
			return new Result<>(true, StatusCode.OK, "更新成功", null);
		}
		return new Result<>(false, StatusCode.ERROR, "更新失败", null);
	}

	@DeleteMapping("/{id}")
	public Result<?> deleteArticle(@PathVariable(value="id") String id) {
		boolean result = articleService.deleteById(id);
		if (result) {
			return new Result<>(true, StatusCode.OK, "删除成功", null);
		}
		return new Result<>(false, StatusCode.ERROR, "删除失败", null);
	}

	@GetMapping("/top")
	public Result<?> topArticle() {
		List<Article> articles = articleService.getTop();
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", articles);
		return result;
	}
	
	@PostMapping("/search")
	public Result<?> search(@RequestBody Article article) {
		List<Article> articles = articleService.search(article);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", articles);
		return result;
	}
	
	@PostMapping("/search/{pageNum}")
	public Result<?> searchPageable(@PathVariable(value="pageNum") int pageNum,
			@RequestBody Article article) {
		List<Article> articles = articleService.searchPageable(article, pageNum);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", articles);
		return result;
	}
	
	@GetMapping("/channel/{columnId}/{pageNum}")
	public Result<?> findByChannelId(@PathVariable(value="columnId") String columnId,
			@PathVariable(value="pageNum") int pageNum) {
		List<Article> articles = articleService.findByChannelId(columnId, pageNum);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", articles);
		return result;
	}
	@GetMapping("/columnId/{columnId}/{pageNum}")
	public Result<?> findByColumnId(@PathVariable(value="columnId") String columnId,
			@PathVariable(value="pageNum") int pageNum) {
		List<Article> articles = articleService.findByByColumnId(columnId, pageNum);
		Result<?> result = new Result<>(true, StatusCode.OK, "查询成功", articles);
		return result;
	}
}
