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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.constant.ArticleConst;
import com.zhihao.tensquare.entity.Article_Column;
import com.zhihao.tensquare.repository.ColumnRepository;
import com.zhihao.tensquare.service.ColumnService;
import com.zhihao.tensquare.util.IdWorker;
import com.zhihao.tensquare.util.PropertyCopyUtil;

/**
 * @author zzh
 * 2018年11月26日
 */
@Service
@Transactional
public class ColumnServiceImpl implements ColumnService {

	@Autowired
	private ColumnRepository repository;

	@Autowired
	private IdWorker worker;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public List<Article_Column> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Article_Column> search(Article_Column column) {
		return repository.findAll(new Specification<Article_Column>() {
			private static final long serialVersionUID = -4829110284573898683L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Article_Column> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (column.getUserId()!=null && !"".equals(column.getUserId())) {
					Predicate predicate = criteriaBuilder.equal(root.get("userId").as(String.class), column.getUserId());
					predicates.add(predicate);
				}
				if (column.getName()!=null && !"".equals(column.getName())) {
					Predicate predicate = criteriaBuilder.equal(root.get("name").as(String.class), column.getName());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

	@Override
	public List<Article_Column> searchPageable(Article_Column column, int pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, ArticleConst.COUNT_PER_PAGE);
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Article_Column>() {
			private static final long serialVersionUID = 2141747588240960775L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Article_Column> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (column.getUserId()!=null && !"".equals(column.getUserId())) {
					Predicate predicate = criteriaBuilder.equal(root.get("userId").as(String.class), column.getUserId());
					predicates.add(predicate);
				}
				if (column.getName()!=null && !"".equals(column.getName())) {
					Predicate predicate = criteriaBuilder.equal(root.get("name").as(String.class), column.getName());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		}, pageRequest).getContent();
	}

	@Override
	public Article_Column findById(String id) {
		Optional<Article_Column> column = repository.findById(id);
		if (column.isPresent()) {
			return column.get();
		}
		return null;
	}

	@Override
	public boolean delete(Article_Column column) {
		repository.delete(column);
		return true;
	}

	@Override
	public boolean deleteById(String id) {
		int result = repository.deleteByIdAndUserId(id, (String)request.getAttribute("userId"));
		if (result == 1)
			return true;
		return false;
	}

	@Override
	public void create(Article_Column column) {
		init(column);
		repository.save(column);
	}

	@Override
	public boolean update(Article_Column column) {
		Article_Column colFromBD = repository.findByIdAndUserId(column.getId(),
				(String)request.getAttribute("userId"));
		if (colFromBD != null) {
			PropertyCopyUtil.copyNonNullProperties(column, colFromBD);
			repository.save(colFromBD);
			return true;
		}
		return false;
	}

	@Override
	public boolean approve(String id) {
		int result = repository.approve(id);
		if (result == 1) {
			return true;
		}
		return false;
	}

	private Article_Column init(Article_Column column) {
		String id = String.valueOf(worker.nextId());
		column.setId(id);
		column.setUserId((String) request.getAttribute("userId"));
		column.setCreateTime(new Date());
		column.setState("0");
		return column;
	}
}
