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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.constant.ArticleConst;
import com.zhihao.tensquare.entity.Article;
import com.zhihao.tensquare.repository.ArticleRepository;
import com.zhihao.tensquare.service.ArticleService;
import com.zhihao.tensquare.util.IdWorker;
import com.zhihao.tensquare.util.PropertyCopyUtil;

/**
 * @author zzh
 * 2018年11月20日
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	
	@Autowired
	private ArticleRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	public boolean approve(String id) {
		int result = repository.updateState(id);
		if (result == 1)
			return true;
		return false;
	}

	@Override
	public int updateLike(String id) {
		return repository.addLike(id);
	}

	@Override
	public void create(Article article) {
		init(article);
		repository.save(article);
	}

	@Override
	public List<Article> listAll() {
		return repository.findAll();
	}

	@Override
	public Article getById(String id) {
		Article article = (Article) redisTemplate.opsForValue().get(ArticleConst.KEY_PREFFIX+id);
		if (article == null) {
			Optional<Article> optional = repository.findById(id);
			if (optional.isPresent()) {
				article = optional.get();
				redisTemplate.opsForValue().set(ArticleConst.KEY_PREFFIX+id, article);
			}
			return article;
		}
		return article;
	}

	@Override
	public boolean update(Article article) {
		Article artFromDB = repository.findByIdAndUserId(article.getId(),
				(String) request.getAttribute("userId"));
		if (artFromDB != null) {
			redisTemplate.delete(ArticleConst.KEY_PREFFIX+article.getId());
			article.setUpdateTime(new Date());
			PropertyCopyUtil.copyNonNullProperties(article, artFromDB);
			repository.save(artFromDB);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteById(String id) {
		int result = repository.deleteByIdAndUserId(id, (String) request.getAttribute("userId"));
		if (result == 1) {
			redisTemplate.delete(ArticleConst.KEY_PREFFIX+id);
			return true;
		}
		return false;
	}

	@Override
	public List<Article> getTop() {
		return repository.findByIsTop(ArticleConst.TOP);
	}

	@Override
	public List<Article> search(Article article) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Article>() {
			private static final long serialVersionUID = 2141747588240960775L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (article.getTitle()!=null && !"".equals(article.getTitle())) {
					Predicate predicate = criteriaBuilder.like(root.get("title").as(String.class), article.getTitle());
					predicates.add(predicate);
				}
				if (article.getContent()!=null && !"".equals(article.getContent())) {
					Predicate predicate = criteriaBuilder.like(root.get("content").as(String.class), article.getContent());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}

	@Override
	public List<Article> searchPageable(Article article, int pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, ArticleConst.COUNT_PER_PAGE);
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Article>() {
			private static final long serialVersionUID = 2141747588240960775L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (article.getTitle()!=null && !"".equals(article.getTitle())) {
					Predicate predicate = criteriaBuilder.like(root.get("title").as(String.class), article.getTitle());
					predicates.add(predicate);
				}
				if (article.getContent()!=null && !"".equals(article.getContent())) {
					Predicate predicate = criteriaBuilder.like(root.get("content").as(String.class), article.getContent());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		}, pageRequest).getContent();
	}

	@Override
	public List<Article> findByChannelId(String channelId, int pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, ArticleConst.COUNT_PER_PAGE);
		return repository.findByChannelId(channelId, pageRequest).getContent();
	}

	@Override
	public List<Article> findByBycolumnId(String columnId, int pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, ArticleConst.COUNT_PER_PAGE);
		return repository.findByColumnId(columnId, pageRequest).getContent();
	}

	private Article init(Article article) {
		String id = String.valueOf(worker.nextId());
		article.setId(id);
		article.setUserId((String) request.getAttribute("userId"));
		article.setCreateTime(new Date());
		article.setUpdateTime(new Date());
		article.setThumbUp(0);
		article.setVisits(0);
		article.setComment(0);
		article.setState("0");
		return article;
	}
}
