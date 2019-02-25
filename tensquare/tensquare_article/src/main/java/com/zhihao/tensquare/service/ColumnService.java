/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Article_Column;

/**
 * @author zzh
 * 2018年11月26日
 */
public interface ColumnService {

	List<Article_Column> findAll();
	
	List<Article_Column> search(Article_Column column);

	List<Article_Column> searchPageable(Article_Column column, int pageNum);

	Article_Column findById(String id);

	boolean delete(Article_Column column);
	
	boolean deleteById(String id);
	
	void create(Article_Column column);
	
	boolean update(Article_Column column);

	boolean approve(String id);
}
