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

import com.zhihao.tensquare.constant.QuestionAnswerConst;
import com.zhihao.tensquare.entity.Reply;
import com.zhihao.tensquare.repository.ReplyRepository;
import com.zhihao.tensquare.service.ReplyService;
import com.zhihao.tensquare.util.IdWorker;
import com.zhihao.tensquare.util.PropertyCopyUtil;

/**
 * @author zzh
 * 2018年11月20日
 */
@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public Reply findById(String id) {
		Optional<Reply> optional = repository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Reply> search(Reply reply, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageRequest = PageRequest.of(pageNum-1, QuestionAnswerConst.PAGE_SIZE);
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Reply>() {
			private static final long serialVersionUID = 2141747588240960775L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (reply.getProblemId()!=null && !"".equals(reply.getProblemId())) {
					Predicate predicate = criteriaBuilder.equal(root.get("jobName").as(String.class), reply.getProblemId());
					predicates.add(predicate);
				}
				if (reply.getUserId()!=null && !"".equals(reply.getUserId())) {
					Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), reply.getUserId());
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
	public Long replyNumberForProblem(String problemId) {
		return repository.count(new Specification<Reply>() {
			private static final long serialVersionUID = 8672867813685089012L;

			@Override
			public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.equal(root.get("problemId").as(String.class), problemId);
				return predicate;
			}
		});
	}

	@Override
	public void create(Reply reply) {
		reply.setId(String.valueOf(worker.nextId()));
		init(reply);
		repository.save(reply);
	}

	@Override
	public boolean update(Reply reply) {
		Reply replyFromDB = repository.findByIdAndUserId(reply.getId(),
				(String) request.getAttribute("userId"));
		if (replyFromDB != null) {
			reply.setUpdateTime(new Date());
			PropertyCopyUtil.copyNonNullProperties(reply, replyFromDB);
			repository.save(replyFromDB);
			return true;
		}
		return false;
	}

	@Override
	public List<Reply> findByProblemId(String problemId, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageRequest = PageRequest.of(pageNum-1, QuestionAnswerConst.PAGE_SIZE);
		return repository.findByProblemIdOrderByUpdateTimeDesc(problemId, pageRequest).getContent();
	}

	@Override
	public List<Reply> findByUserId(String userId, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageRequest = PageRequest.of(pageNum-1, QuestionAnswerConst.PAGE_SIZE);
		return repository.findByUserIdOrderByUpdateTimeDesc(userId, pageRequest).getContent();
	}

	@Override
	public Long replyNumberForUser(String userId) {
		return repository.count(new Specification<Reply>() {
			private static final long serialVersionUID = -8899655784302600675L;

			@Override
			public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.equal(root.get("userId").as(String.class), userId);
				return predicate;
			}
		});
	}

	@Override
	public Long replyNumberForUserAndProblem(String problemId, String userId) {
		return repository.count(new Specification<Reply>() {
			private static final long serialVersionUID = -2976462111524729238L;

			@Override
			public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate predicate1 = criteriaBuilder.equal(root.get("userId").as(String.class), userId);
				Predicate predicate2 = criteriaBuilder.equal(root.get("problemId").as(String.class), problemId);
				predicates.add(predicate1);
				predicates.add(predicate2);
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

	private Reply init(Reply reply) {
		reply.setCreateTime(new Date());
		reply.setUpdateTime(new Date());
		return reply;
	}
}
