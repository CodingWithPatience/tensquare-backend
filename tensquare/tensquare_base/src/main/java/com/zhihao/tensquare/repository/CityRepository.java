/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhihao.tensquare.entity.City;

/**
 * @author zzh
 * 2018年11月19日
 */
public interface CityRepository extends JpaRepository<City, String>,
		JpaSpecificationExecutor<City> {

}
