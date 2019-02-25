/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.entity.Friend;
import com.zhihao.tensquare.entity.NoFriend;
import com.zhihao.tensquare.repository.FriendRepository;
import com.zhihao.tensquare.repository.NoFriendRepository;
import com.zhihao.tensquare.service.FriendService;

/**
 * @author zzh
 * 2018年11月28日
 */
@Service
@Transactional
public class FriendServiceImpl implements FriendService {

	@Autowired
	private FriendRepository friendRepository;

	@Autowired
	private NoFriendRepository noFriendRepository;

	@Override
	public void friend(String userId, String friendId) {
		Friend friend = friendRepository.findByUserIdAndFriendId(userId, friendId);
		if (friend != null) {
			throw new RuntimeException("不能重复关注好友");
		}
		NoFriend noFriend = noFriendRepository.findByUserIdAndFriendId(userId, friendId);
		if (noFriend!=null) {
			noFriendRepository.deleteByUserIdAndFriendId(userId, friendId);
		}
		friend = new Friend();
		friend.setUserId(userId);
		friend.setFriendId(friendId);
		friend.setIsLike("0");
		friendRepository.save(friend);
		friend = friendRepository.findByUserIdAndFriendId(friendId, userId);
		if (friend != null) {
			friendRepository.updateIsLike(userId, friendId, "1");
			friendRepository.updateIsLike(friendId, userId, "1");
		}
	}

	@Override
	public void noFriend(String userId, String friendId) {
		NoFriend noFriend = noFriendRepository.findByUserIdAndFriendId(userId, friendId);
		if (noFriend != null) {
			throw new RuntimeException("不能重复加入到非好友列表中");
		}
		Friend friend = friendRepository.findByUserIdAndFriendId(userId, friendId);
		if (friend != null) {
			friendRepository.deleteByUserIdAndFriendId(userId, friendId);
		}
		noFriend = new NoFriend();
		noFriend.setUserId(userId);
		noFriend.setFriendId(friendId);
		noFriendRepository.save(noFriend);
	}

	@Override
	public void deleteByUserIdAndFriendId(String userId, String friendId) {
		friendRepository.deleteByUserIdAndFriendId(userId, friendId);
		friendRepository.updateIsLike(friendId, userId, "0");
	}
	
}
