/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Gathering;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface GatheringService {

	List<Gathering> findAll();
	
	Gathering findById(String id);
	
	List<Gathering> search(Gathering gathering);

	List<Gathering> searchPageable(Gathering gathering, int pageNum);

	List<Gathering> findByCityId(String cityId, int pageNum);

	void delete(Gathering gathering);
	
	void deleteById(String id);
	
	void create(Gathering gathering);
	
	void update(Gathering gathering);

}
