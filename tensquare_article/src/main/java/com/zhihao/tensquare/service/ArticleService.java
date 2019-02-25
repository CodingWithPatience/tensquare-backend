/**
 * 
 */
package com.zhihao.tensquare.service;

import com.zhihao.tensquare.entity.Article;

import java.util.List;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface ArticleService {

	boolean approve(String id);

	int updateLike(String id);

	void create(Article article);

	List<Article> listAll();

	Article getById(String id);

	boolean update(Article article);

	boolean deleteById(String id);

	List<Article> getTop();

	List<Article> search(Article article);

	List<Article> searchPageable(Article article, int pageNum);

	List<Article> findByChannelId(String channelId, int pageNum);

	List<Article> findByByColumnId(String columnId, int pageNum);
}
