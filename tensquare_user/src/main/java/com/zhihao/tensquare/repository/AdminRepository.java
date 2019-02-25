/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhihao.tensquare.entity.Admin;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface AdminRepository extends JpaRepository<Admin, String>,
		JpaSpecificationExecutor<Admin> {

	Admin findByLoginName(String loginName);
}
