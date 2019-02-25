/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import com.zhihao.tensquare.constant.EnterpriseConst;
import com.zhihao.tensquare.entity.Enterprise;
import com.zhihao.tensquare.repository.EnterpriseRepository;
import com.zhihao.tensquare.service.EnterpriseService;
import com.zhihao.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzh
 * 2018年11月20日
 */
@Service
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {

	
	@Autowired
	private EnterpriseRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器

	@Override
	public List<Enterprise> findAll() {
		return repository.findAll();
	}

	@Override
	public Enterprise findById(String id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Enterprise> search(Enterprise enterprise) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Enterprise>() {
			private static final long serialVersionUID = -4691739206390600230L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (enterprise.getName()!=null && !"".equals(enterprise.getName())) {
					Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%"+enterprise.getName()+"%");
					predicates.add(predicate);
				}
				if (enterprise.getIsHot()!=null && !"".equals(enterprise.getIsHot())) {
					Predicate predicate = criteriaBuilder.equal(root.get("isHot").as(String.class), enterprise.getIsHot());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

	@Override
	public void delete(Enterprise enterprise) {
		repository.delete(enterprise);
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
	public void create(Enterprise enterprise) {
		enterprise.setId(String.valueOf(worker.nextId()));
		repository.save(enterprise);
	}

	@Override
	public void update(Enterprise enterprise) {
		repository.save(enterprise);
	}

	@Override
	public List<Enterprise> searchPageable(Enterprise enterprise, int pageNum) {
	    pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageable = PageRequest.of(pageNum-1, EnterpriseConst.PAGE_SIZE);
		return repository.findAll(new Specification<Enterprise>() {
			@Override
			public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (enterprise.getName() != null && !"".equals(enterprise.getName())) {
					Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%"+enterprise.getName()+"%");
					predicates.add(predicate);
				}
				if (enterprise.getIsHot() != null && !"".equals(enterprise.getIsHot())) {
					Predicate predicate = criteriaBuilder.equal(root.get("isHot").as(String.class), enterprise.getIsHot());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		}, pageable).getContent();
	}

	@Override
	public List<Enterprise> findByIsHot(String isHot) {
		return repository.findByIsHot(isHot);
	}

}
