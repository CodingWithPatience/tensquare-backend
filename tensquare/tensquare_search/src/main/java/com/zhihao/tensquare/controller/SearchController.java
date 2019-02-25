/**
 * 
 */
package com.zhihao.tensquare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.zhihao.tensquare.constant.StatusCode;
import com.zhihao.tensquare.dto.Result;
import com.zhihao.tensquare.entity.Article;
import com.zhihao.tensquare.service.ArticleService;

/**
 * @author zzh
 * 2018年11月30日
 */
@RestController
@RequestMapping("search")
@CrossOrigin
public class SearchController {

	@Autowired
	private ArticleService articleService;
	
	@PostMapping("article")
	public Result<?> save(@RequestBody Article article) {
		articleService.save(article);
		return new Result<>(true, StatusCode.OK, "添加成功", null);
	}
	
	@GetMapping("article/{pageNum}")
	public Result<?> searchByKeyword(@RequestParam("keyword") String keyword,
			@PathVariable("pageNum") int pageNum) {
		keyword = HtmlUtils.htmlEscape(keyword);
		List<Article> articles = articleService.findByKeyword(keyword, pageNum);
		return new Result<>(true, StatusCode.OK, "查询成功", articles);
	}
}
