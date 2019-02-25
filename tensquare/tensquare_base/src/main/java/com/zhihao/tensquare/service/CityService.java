/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.City;

/**
 * @author zzh
 * 2018年11月19日
 */
public interface CityService {

	List<City> findAll();
	
	City findById(String id);
	
	List<City> search(City city);

	void delete(City city);
	
	void deleteById(String id);
	
	Long total();
	
	void create(City city);
	
	void update(City city);
}
