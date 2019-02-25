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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.constant.QuestionAnswerConst;
import com.zhihao.tensquare.entity.Problem;
import com.zhihao.tensquare.repository.ProblemRepository;
import com.zhihao.tensquare.service.ProblemService;
import com.zhihao.tensquare.util.IdWorker;
import com.zhihao.tensquare.util.PropertyCopyUtil;

/**
 * @author zzh
 * 2018年11月20日
 */
@Service
@Transactional
public class ProblemServiceImpl implements ProblemService {

	
	@Autowired
	private ProblemRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public List<Problem> findAll() {
		return repository.findAll();
	}

	@Override
	public Problem findById(String id) {
		Optional<Problem> optional = repository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Problem> searchPageable(Problem problem, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageRequest = PageRequest.of(pageNum-1, QuestionAnswerConst.PAGE_SIZE);
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Problem>() {
			private static final long serialVersionUID = -4691739206390600230L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (problem.getTitle()!=null && !"".equals(problem.getTitle())) {
					Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%"+problem.getTitle()+"%");
					predicates.add(predicate);
				}
				if (problem.getSolve()!=null && !"".equals(problem.getSolve())) {
					Predicate predicate = criteriaBuilder.equal(root.get("isHot").as(String.class), problem.getSolve());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		}, pageRequest).getContent();
	}

	@Override
	public boolean deleteById(String id) {
		int result = repository.deleteByIdAndUserId(id, 
				(String) request.getAttribute("userId"));
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public Long total() {
		return repository.count();
	}

	@Override
	public void create(Problem problem) {
		init(problem);
		repository.save(problem);
	}

	@Override
	public boolean update(Problem problem) {
		//不能直接执行更新操作，因为Problem对象部分属性为null
		//若直接更新，null属性也会写进到数据库中，覆盖了数据库已有的值，造成数据丢失
		//先从数据库中查询出对应的数据，将要更新的字段写到对象中，再更新
		Problem proFromDB = repository.findByIdAndUserId(problem.getId(),
				(String) request.getAttribute("userId"));
		if (proFromDB != null) {
			//设置更新时间
			problem.setUpdateTime(new Date());
			//将problem中不为null的属性(即要更新的属性)copy到proFromDB中去
			//然后再执行更新操作
			PropertyCopyUtil.copyNonNullProperties(problem, proFromDB);
			repository.save(proFromDB);
			return true;
		}
		return false;
	}

	@Override
	public List<Problem> findUnsolvedProblem(String labelId, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageRequest = PageRequest.of(pageNum-1, QuestionAnswerConst.PAGE_SIZE);
		if (labelId.equals("0"))
			return repository.findUnsolved(pageRequest).getContent();
		return repository.findUnsolvedByLabelId(labelId, pageRequest).getContent();
	}

	@Override
	public List<Problem> findNewestProblem(String labelId, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageRequest = PageRequest.of(pageNum-1, QuestionAnswerConst.PAGE_SIZE);
		if (labelId.equals("0"))
			return repository.findNewest(pageRequest).getContent();
		return repository.findNewestByLabelId(labelId, pageRequest).getContent();
	}

	@Override
	public List<Problem> findHotProblem(String labelId, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageRequest = PageRequest.of(pageNum-1, QuestionAnswerConst.PAGE_SIZE);
		if (labelId.equals("0"))
			return repository.findHot(pageRequest).getContent();
		return repository.findHotByLabelId(labelId, pageRequest).getContent();
	}

	@Override
	public Long problemNumberForLabel(String labelId) {
		return repository.problemNumberForLabel(labelId);
	}

	@Override
	public List<Problem> findAllByLabelId(String labelId, int pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, QuestionAnswerConst.PAGE_SIZE);
		return repository.findByLabelId(labelId, pageRequest).getContent();
	}

	@Override
	public List<Problem> search(Problem problem) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Problem>() {
			private static final long serialVersionUID = -4691739206390600230L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (problem.getTitle()!=null && !"".equals(problem.getTitle())) {
					Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%"+problem.getTitle()+"%");
					predicates.add(predicate);
				}
				if (problem.getSolve()!=null && !"".equals(problem.getSolve())) {
					Predicate predicate = criteriaBuilder.equal(root.get("isHot").as(String.class), problem.getSolve());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

	@Override
	public List<Problem> findWaitToAnswer(String labelId, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		Pageable pageable = PageRequest.of(pageNum-1, QuestionAnswerConst.PAGE_SIZE);
		if (labelId.equals("0"))
			return repository.findWait(pageable).getContent();
		List<Problem> problems = repository.findWaitToAnswer(labelId, pageable).getContent();
		return problems;
	}

	//初始化Problem对象，完善部分用户没有填写的信息
	private Problem init(Problem problem) {
		problem.setId(String.valueOf(worker.nextId()));
		problem.setCreateTime(new Date());
		problem.setUpdateTime(new Date());
		problem.setReplyNumber(0L);
		problem.setVisits(0L);
		problem.setThumbUp(0L);
		problem.setSolve(QuestionAnswerConst.UN_SOLVE);
		return problem;
	}
}
