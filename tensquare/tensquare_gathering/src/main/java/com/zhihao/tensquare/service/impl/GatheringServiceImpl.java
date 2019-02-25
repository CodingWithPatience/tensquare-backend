/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.constant.GatheringConst;
import com.zhihao.tensquare.entity.Gathering;
import com.zhihao.tensquare.repository.GatheringRepository;
import com.zhihao.tensquare.service.GatheringService;
import com.zhihao.tensquare.util.IdWorker;

/**
 * @author zzh
 * 2018年11月20日
 */
@Service
@Transactional
public class GatheringServiceImpl implements GatheringService {

	
	@Autowired
	private GatheringRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器

	@Override
	public List<Gathering> findAll() {
		return repository.findAll();
	}

	@Override
	@Cacheable(value="gathering", key="#id")
	public Gathering findById(String id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Gathering> search(Gathering gathering) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Gathering>() {
			private static final long serialVersionUID = -4691739206390600230L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (gathering.getName()!=null && !"".equals(gathering.getName())) {
					Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%"+gathering.getName()+"%");
					predicates.add(predicate);
				}
				if (gathering.getCityId()!=null && !"".equals(gathering.getCityId())) {
					Predicate predicate = criteriaBuilder.equal(root.get("city").as(String.class), gathering.getCityId());
					predicates.add(predicate);
				}
				if (gathering.getStartTime()!=null) {
					Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("startTime").as(Date.class), gathering.getStartTime());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

	@Override
	@CacheEvict(value="gathering", key="#gathering.id")
	public void delete(Gathering gathering) {
		repository.delete(gathering);
	}

	@Override
	@CacheEvict(value="gathering", key="#.id")
	public void deleteById(String id) {
		repository.deleteById(id);
	}

	@Override
	public void create(Gathering gathering) {
		gathering.setId(String.valueOf(worker.nextId()));
		repository.save(gathering);
	}

	@Override
	@CacheEvict(value="gathering", key="#gathering.id")
	public void update(Gathering gathering) {
		repository.save(gathering);
	}

	@Override
	public List<Gathering> findByCityId(String cityId, int pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, GatheringConst.COUNT_PER_PAGE);
		return repository.findByCityId(cityId, pageRequest).getContent();
	}

	@Override
	public List<Gathering> searchPageable(Gathering gathering, int pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, GatheringConst.COUNT_PER_PAGE);
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Gathering>() {
			private static final long serialVersionUID = -4691739206390600230L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (gathering.getName()!=null && !"".equals(gathering.getName())) {
					Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%"+gathering.getName()+"%");
					predicates.add(predicate);
				}
				if (gathering.getCityId()!=null && !"".equals(gathering.getCityId())) {
					Predicate predicate = criteriaBuilder.equal(root.get("city").as(String.class), gathering.getCityId());
					predicates.add(predicate);
				}
				if (gathering.getStartTime()!=null) {
					Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("startTime").as(Date.class), gathering.getStartTime());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		}, pageRequest).getContent();
	}

}
