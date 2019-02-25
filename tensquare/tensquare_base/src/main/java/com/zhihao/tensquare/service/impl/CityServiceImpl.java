/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.entity.City;
import com.zhihao.tensquare.repository.CityRepository;
import com.zhihao.tensquare.service.CityService;
import com.zhihao.tensquare.util.IdWorker;

/**
 * @author zzh
 * 2018年11月19日
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {
	@Autowired
	private CityRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器

	@Override
	public List<City> findAll() {
		return repository.findAll();
	}

	@Override
	public City findById(String id) {
		return repository.findById(id).get();
	}

	@Override
	public void delete(City city) {
		repository.delete(city);
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
	public void create(City city) {
		String id = String.valueOf(worker.nextId());
		city.setId(id);
		repository.save(city);
	}

	@Override
	public void update(City city) {
		repository.save(city);
	}

	@Override
	public List<City> search(City city) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<City>() {
			private static final long serialVersionUID = 6740182037532349329L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<City> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (city.getName()!=null && !"".equals(city.getName())) {
					Predicate predicate = criteriaBuilder.equal(root.get("name").as(String.class), city.getName());
					predicates.add(predicate);
				}
				if (city.getIsHot()!=null && !"".equals(city.getIsHot())) {
					Predicate predicate = criteriaBuilder.equal(root.get("isHot").as(String.class), city.getIsHot());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

}
