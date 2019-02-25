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

import com.zhihao.tensquare.entity.Label;
import com.zhihao.tensquare.repository.LabelRepository;
import com.zhihao.tensquare.service.LabelService;
import com.zhihao.tensquare.util.IdWorker;

/**
 * @author zzh
 * 2018年11月19日
 */
@Service
@Transactional
public class LabelServiceImpl implements LabelService {
	@Autowired
	private LabelRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器

	@Override
	public List<Label> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Label> topList() {
		return repository.findByState("1");
	}

	@Override
	public Label findById(String id) {
		return repository.findById(id).get();
	}

	@Override
	public void delete(Label label) {
		repository.delete(label);
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
	public void create(Label label) {
		String id = String.valueOf(worker.nextId());
		label.setId(id);
		repository.save(label);
	}

	@Override
	public void update(Label label) {
		repository.save(label);
	}

	@Override
	public List<Label> search(Label label) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Label>() {
			private static final long serialVersionUID = 6740182037532349329L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) { List<Predicate> predicates = new ArrayList<>();
				if (label.getLabelName()!=null && !"".equals(label.getLabelName())) {
					Predicate predicate = criteriaBuilder.like(root.get("labelName").as(String.class), "%"+label.getLabelName()+"%");
					predicates.add(predicate);
				}
				if (label.getState()!=null && !"".equals(label.getState())) {
					Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

}
