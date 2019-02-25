/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Article;

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

	List<Article> findByBycolumnId(String columnId, int pageNum);
}
