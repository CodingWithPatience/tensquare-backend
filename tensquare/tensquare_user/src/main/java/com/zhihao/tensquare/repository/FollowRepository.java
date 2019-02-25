/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhihao.tensquare.entity.Follow;

/**
 * @author zzh
 * 2018年12月5日
 */
public interface FollowRepository extends JpaRepository<Follow, String> {

	Follow findByUserIdAndTargetId(String userId, String targetId);

	void deleteByUserIdAndTargetId(String userId, String targetId);
}
