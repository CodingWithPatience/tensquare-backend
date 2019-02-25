/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zhihao.tensquare.entity.Friend;

/**
 * @author zzh
 * 2018年11月28日
 */
public interface FriendRepository extends JpaRepository<Friend, String> {

	Friend findByUserIdAndFriendId(String userId, String friendId);
	
	@Modifying
	@Query(value="update tb_friend set islike = ?3 where userid = ?1 and friendid = ?2", nativeQuery=true)
	void updateIsLike(String userId, String friendId, String isLike);

	void deleteByUserIdAndFriendId(String userId, String friendId);
}
