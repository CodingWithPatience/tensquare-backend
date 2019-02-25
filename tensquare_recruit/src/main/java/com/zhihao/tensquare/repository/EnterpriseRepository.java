/**
 * 
 */
package com.zhihao.tensquare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhihao.tensquare.entity.Enterprise;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface EnterpriseRepository extends JpaRepository<Enterprise, String>,
		JpaSpecificationExecutor<Enterprise> {

	List<Enterprise> findByIsHot(String isHot);
}
