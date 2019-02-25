/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Recruit;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface RecruitService {

	List<Recruit> findAll();
	
	Recruit findById(String id);
	
	List<Recruit> search(Recruit recruit);

	List<Recruit> findRecommandJob();

	List<Recruit> findNewestJob();

	void delete(Recruit recruit);
	
	void deleteById(String id);
	
	Long total();
	
	void create(Recruit recruit);
	
	void update(Recruit recruit);

}
