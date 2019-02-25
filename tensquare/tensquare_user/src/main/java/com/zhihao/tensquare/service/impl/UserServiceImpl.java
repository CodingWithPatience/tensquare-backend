/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.constant.RoleConst;
import com.zhihao.tensquare.entity.Follow;
import com.zhihao.tensquare.entity.User;
import com.zhihao.tensquare.repository.FollowRepository;
import com.zhihao.tensquare.repository.UserRepository;
import com.zhihao.tensquare.service.UserService;
import com.zhihao.tensquare.util.IdWorker;

import io.jsonwebtoken.Claims;

/**
 * @author zzh
 * 2018年11月20日
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private IdWorker worker;       //分布式id生成器
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findById(String id) {
		Optional<User> optional = repository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<User> search(User user) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<User>() {
			private static final long serialVersionUID = -4691739206390600230L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (user.getLoginName()!=null && !"".equals(user.getLoginName())) {
					Predicate predicate = criteriaBuilder.like(root.get("loginName").as(String.class), "%"+user.getLoginName()+"%");
					predicates.add(predicate);
				}
				if (user.getNickName()!=null && !"".equals(user.getNickName())) {
					Predicate predicate = criteriaBuilder.equal(root.get("nickName").as(String.class), user.getNickName());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

	@Override
	public void delete(User user) {
		repository.delete(user);
	}

	@Override
	public void deleteById(String id) throws RuntimeException {
		//在JwtInterceptor拦截器中校验用户角色是否为admin，若是，
		//在HttpServletRequest对象中添加admin_claims属性
		Claims claims = (Claims) request.getAttribute("admin_claims");
		if (claims == null) {
			throw new RuntimeException("权限不足");
		}
		repository.deleteById(id);
	}

	@Override
	public Long total() {
		return repository.count();
	}

	@Override
	public void create(User user) {
		user.setId(String.valueOf(worker.nextId()));
		init(user);             //初始化用户，将用户注册时不需要填写的部分信息，补充完整
		String encodedPass = encoder.encode(user.getPassword());
		user.setPassword(encodedPass);
		repository.save(user);
	}

	@Override
	public void update(User user) {
		repository.save(user);
	}

	@Override
	public List<User> searchPageable(User user, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum-1, RoleConst.USER_PAGE_SIZE);
		//Specification用于封装查询条件
		return repository.findAll(new Specification<User>() {
			private static final long serialVersionUID = -4691739206390600230L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (user.getLoginName()!=null && !"".equals(user.getLoginName())) {
					Predicate predicate = criteriaBuilder.like(root.get("loginName").as(String.class), "%"+user.getLoginName()+"%");
					predicates.add(predicate);
				}
				if (user.getNickName()!=null && !"".equals(user.getNickName())) {
					Predicate predicate = criteriaBuilder.equal(root.get("nickName").as(String.class), user.getNickName());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		}, pageable).getContent();
	}
	
	private void init(User user) {
		user.setFansCount(0);
		user.setFollowCount(0);
		user.setOnlineTime(0L);
		user.setRegisterDate(new Date());
		user.setUpdateDate(new Date());
	}

	@Override
	public User findByNameAndPass(String loginName, String password) {
		User user = repository.findByLoginName(loginName);
		if (user!=null && encoder.matches(password, user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public User findByMobileAndPass(String mobile, String password) {
		User user = repository.findByMobile(mobile);
		if (user!=null && encoder.matches(password, user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public void follow(String userId, String friendId) {
		//权限校验已在服务调用方tensquare_friend中完成
		//因此，在这里不用验证用户权限
		repository.updateFollowCount(userId, 1);
		repository.updateFansCount(friendId, 1);
		Follow follow = new Follow();
		follow.setUserId(userId);
		follow.setTargetId(friendId);
		followRepository.save(follow);
	}

	@Override
	public void unfollow(String userId, String friendId) {
		//权限校验已在服务调用方tensquare_friend中完成
		//因此，在这里不用验证用户权限
		repository.updateFollowCount(userId, -1);
		repository.updateFansCount(friendId, -1);
		followRepository.deleteByUserIdAndTargetId(userId, friendId);
	}

	@Override
	public void updateLoginTime(String id) {
		repository.updateLoginTime(id);
	}

}
