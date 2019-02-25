/**
 * 
 */
package com.zhihao.tensquare.service;

/**
 * @author zzh
 * 2018年11月28日
 */
public interface FriendService {

	void friend(String userId, String friendId);

	void noFriend(String userId, String friendId);

	void deleteByUserIdAndFriendId(String userId, String friendId);
}
