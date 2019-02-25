/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.User;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface UserService {

	List<User> findAll();
	
	User findById(String id);
	
	User findByNameAndPass(String loginName, String password);

	User findByMobileAndPass(String mobile, String password);
	
	List<User> search(User user);

	void delete(User user);
	
	void deleteById(String id) throws RuntimeException;
	
	Long total();
	
	void create(User user);
	
	void update(User user);

	List<User> searchPageable(User user, int pageNum);

	void follow(String userId, String friendId);

	void unfollow(String userId, String friendId);

	void updateLoginTime(String id);
}
