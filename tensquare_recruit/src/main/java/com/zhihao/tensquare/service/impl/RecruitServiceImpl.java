/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import com.zhihao.tensquare.constant.RecruitConst;
import com.zhihao.tensquare.entity.Recruit;
import com.zhihao.tensquare.repository.RecruitRepository;
import com.zhihao.tensquare.service.RecruitService;
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
public class RecruitServiceImpl implements RecruitService {
	
	@Autowired
	private RecruitRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器

	@Override
	public List<Recruit> findAll() {
		return repository.findAll();
	}

	@Override
	public Recruit findById(String id) {
		return repository.findById(id).get();
	}

	@Override
	public List<Recruit> search(Recruit recruit) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Recruit>() {
			private static final long serialVersionUID = 2141747588240960775L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (recruit.getJobName()!=null && !"".equals(recruit.getJobName())) {
					Predicate predicate = criteriaBuilder.like(root.get("jobName").as(String.class), "%"+recruit.getJobName()+"%");
					predicates.add(predicate);
				}
				if (recruit.getState()!=null && !"".equals(recruit.getState())) {
					Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), recruit.getState());
					predicates.add(predicate);
				}
				if (recruit.getLabel() != null && !"".equals(recruit.getLabel())) {
					Predicate predicate = criteriaBuilder.equal(root.get("label").as(String.class), recruit.getLabel());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

	@Override
	public void delete(Recruit recruit) {
		repository.delete(recruit);
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
	public void create(Recruit recruit) {
		recruit.setId(String.valueOf(worker.nextId()));
		repository.save(recruit);
	}

	@Override
	public void update(Recruit recruit) {
		repository.save(recruit);
	}

	@Override
	public List<Recruit> searchPageable(Recruit recruit, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageable = PageRequest.of(pageNum-1, RecruitConst.PAGE_SIZE);
		return repository.findAll(new Specification<Recruit>() {
			@Override
			public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (recruit.getJobName() != null && !"".equals(recruit.getJobName())) {
					Predicate predicate = criteriaBuilder.like(root.get("jobName").as(String.class), "%"+recruit.getJobName()+"%");
					predicates.add(predicate);
				}
				if (recruit.getState() != null && !"".equals(recruit.getState())) {
					Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), recruit.getState());
					predicates.add(predicate);
				}
				if (recruit.getLabel() != null && !"".equals(recruit.getLabel())) {
					Predicate predicate = criteriaBuilder.equal(root.get("label").as(String.class), recruit.getLabel());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		}, pageable).getContent();
	}

	@Override
	public List<Recruit> findRecommendJob() {
		return repository.findTop10ByStateOrderByCreateTimeDesc(RecruitConst.RECOMMEND);
	}

	@Override
	public List<Recruit> findNewestJob() {
		return repository.findTop10ByStateNotOrderByCreateTimeDesc(RecruitConst.CLOSED);
	}

}
