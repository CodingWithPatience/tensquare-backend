/**
 * 
 */
package com.zhihao.tensquare.service;

import com.zhihao.tensquare.entity.Enterprise;

import java.util.List;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface EnterpriseService {

	List<Enterprise> findAll();
	
	Enterprise findById(String id);
	
	List<Enterprise> search(Enterprise enterprise);

	List<Enterprise> findByIsHot(String isHot);

	void delete(Enterprise enterprise);
	
	void deleteById(String id);
	
	Long total();
	
	void create(Enterprise enterprise);
	
	void update(Enterprise enterprise);

    List<Enterprise> searchPageable(Enterprise enterprise, int pageNum);
}
