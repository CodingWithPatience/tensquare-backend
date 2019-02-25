/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Spit;

/**
 * @author zzh
 * 2018年11月28日
 */
public interface SpitService {

	List<Spit> findAll();
	
	Spit findById(String id);
	
	List<Spit> findByParentId(String parentId, int pageNum);
	
	List<Spit> search(Spit spit);
	
	List<Spit> searchPageable(Spit spit, int pageNum);
	
	void update(Spit spit);
	
	void deleteById(String id);
	
	void create(Spit spit);
	
	boolean like(String id, String userId);
}
