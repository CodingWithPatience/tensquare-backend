/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zhihao.tensquare.entity.Reply;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface ReplyRepository extends JpaRepository<Reply, String>,
		JpaSpecificationExecutor<Reply> {
	
	Page<Reply> findByProblemIdOrderByUpdateTimeDesc(String problemId, Pageable pageable);
	
	Page<Reply> findByUserIdOrderByUpdateTimeDesc(String userId, Pageable pageable);
	
	Reply findByIdAndUserId(String id, String userId);
	
	@Modifying
	@Query(value="delete from tb_reply where id = ?1 and userid = ?2", nativeQuery=true)
	int deleteByIdAndUserId(String id, String userId);
}
