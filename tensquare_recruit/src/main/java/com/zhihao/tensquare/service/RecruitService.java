/**
 * 
 */
package com.zhihao.tensquare.service;

import com.zhihao.tensquare.entity.Recruit;

import java.util.List;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface RecruitService {

	List<Recruit> findAll();
	
	Recruit findById(String id);
	
	List<Recruit> search(Recruit recruit);

	List<Recruit> findRecommendJob();

	List<Recruit> findNewestJob();

	void delete(Recruit recruit);
	
	void deleteById(String id);
	
	Long total();
	
	void create(Recruit recruit);
	
	void update(Recruit recruit);

    List<Recruit> searchPageable(Recruit recruit, int pageNum);
}
