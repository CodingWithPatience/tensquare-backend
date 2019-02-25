/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhihao.tensquare.entity.NoFriend;

/**
 * @author zzh
 * 2018年11月28日
 */
public interface NoFriendRepository extends JpaRepository<NoFriend, String> {

	NoFriend findByUserIdAndFriendId(String userId, String friendId);

	void deleteByUserIdAndFriendId(String userId, String friendId);
}
