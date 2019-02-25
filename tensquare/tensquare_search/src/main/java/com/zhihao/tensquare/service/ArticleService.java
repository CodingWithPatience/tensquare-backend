/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Article;

/**
 * @author zzh
 * 2018年11月30日
 */
public interface ArticleService {

	void save(Article article);
	
	List<Article> findByKeyword(String keyword, int pageNum);
}
