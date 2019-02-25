/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.constant.RoleConst;
import com.zhihao.tensquare.entity.Admin;
import com.zhihao.tensquare.repository.AdminRepository;
import com.zhihao.tensquare.service.AdminService;
import com.zhihao.tensquare.util.IdWorker;

/**
 * @author zzh
 * 2018年11月20日
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public List<Admin> findAll() {
		return repository.findAll();
	}

	@Override
	public Admin findById(String id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Admin> search(Admin admin) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Admin>() {
			private static final long serialVersionUID = 2141747588240960775L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (admin.getLoginName()!=null && !"".equals(admin.getLoginName())) {
					Predicate predicate = criteriaBuilder.equal(root.get("jobName").as(String.class), admin.getLoginName());
					predicates.add(predicate);
				}
				if (admin.getState()!=null && !"".equals(admin.getState())) {
					Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), admin.getState());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

	@Override
	public Map<String, Object> getInfo() {
	    Map<String, Object> info = new HashMap<>();
	    List<String> roles = new ArrayList<>();
	    roles.add("admin");
	    info.put("roles", roles);
	    info.put("name", "admin");
	    info.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
	    return info;
    }

	@Override
	public void delete(Admin admin) {
		repository.delete(admin);
	}

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

	@Override
	public Long total() {
		return repository.count();
	}

	@Override
	public void create(Admin admin) {
		admin.setId(String.valueOf(worker.nextId()));
		String encodedPass = encoder.encode(admin.getPassword());
		admin.setPassword(encodedPass);
		repository.save(admin);
	}

	@Override
	public void update(Admin admin) {
		repository.save(admin);
	}

	@Override
	public Admin findByNameAndPass(String loginName, String password) {
		Admin admin = repository.findByLoginName(loginName);
		if (admin!=null && encoder.matches(password, admin.getPassword())) {
			return admin;
		}
		return null;
	}

	@Override
	public List<Admin> searchPageable(Admin admin, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum-1, RoleConst.USER_PAGE_SIZE);
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Admin>() {
			private static final long serialVersionUID = -4691739206390600230L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (admin.getLoginName()!=null && !"".equals(admin.getLoginName())) {
					Predicate predicate = criteriaBuilder.like(root.get("loginName").as(String.class), "%"+admin.getLoginName()+"%");
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		}, pageable).getContent();
	}

}
