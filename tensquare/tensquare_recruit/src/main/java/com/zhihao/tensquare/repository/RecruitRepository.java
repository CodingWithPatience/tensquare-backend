/**
 * 
 */
package com.zhihao.tensquare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhihao.tensquare.entity.Recruit;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface RecruitRepository extends JpaRepository<Recruit, String>,
		JpaSpecificationExecutor<Recruit> {

	List<Recruit> findTop10ByStateOrderByCreateTimeDesc(String state);

	List<Recruit> findTop10ByStateNotOrderByCreateTimeDesc(String state);
}
