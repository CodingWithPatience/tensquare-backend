/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zhihao.tensquare.entity.Article_Column;

/**
 * @author zzh
 * 2018年11月26日
 */
public interface ColumnRepository extends JpaRepository<Article_Column, String>,
		JpaSpecificationExecutor<Article_Column> {

	@Modifying
	@Query(value="update tb_column set state='1', checktime=sysdate() where id = ?", nativeQuery=true)
	int approve(String id);

	Article_Column findByIdAndUserId(String id, String userId);
	
	@Modifying
	int deleteByIdAndUserId(String id, String userId);
}
