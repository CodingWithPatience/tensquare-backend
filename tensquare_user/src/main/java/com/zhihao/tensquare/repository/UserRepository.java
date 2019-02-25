/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zhihao.tensquare.entity.User;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface UserRepository extends JpaRepository<User, String>,
		JpaSpecificationExecutor<User> {
	
	User findByLoginName(String loginName);

	User findByMobile(String mobile);
	
	@Modifying
	@Query(value="update tb_user set fanscount = fanscount+?2 where id = ?1", nativeQuery=true)
	void updateFansCount(String id, int count);

	@Modifying
	@Query(value="update tb_user set followcount = followcount+?2 where id = ?1", nativeQuery=true)
	void updateFollowCount(String id, int count);

	@Modifying
	@Query(value="update tb_user set lastlogin = sysdate() where id = ?", nativeQuery=true)
	void updateLoginTime(String id);
}
