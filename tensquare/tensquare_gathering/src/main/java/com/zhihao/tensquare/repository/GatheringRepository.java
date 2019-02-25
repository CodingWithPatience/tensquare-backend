/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhihao.tensquare.entity.Gathering;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface GatheringRepository extends JpaRepository<Gathering, String>,
		JpaSpecificationExecutor<Gathering> {

	Page<Gathering> findByCityId(String cityId, Pageable pageable);
}
