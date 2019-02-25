/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.constant.ArticleConst;
import com.zhihao.tensquare.entity.Channel;
import com.zhihao.tensquare.repository.ChannelRepository;
import com.zhihao.tensquare.service.ChannelService;
import com.zhihao.tensquare.util.IdWorker;

/**
 * @author zzh
 * 2018年11月20日
 */
@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {
	
	@Autowired
	private ChannelRepository repository;
	
	@Autowired
	private IdWorker worker;       //分布式id生成器

	@Override
	public Channel findById(String id) {
		Optional<Channel> channel = repository.findById(id);
		if (channel.isPresent()) {
			return channel.get();
		}
		return null;
	}

	@Override
	public List<Channel> search(Channel channel) {
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Channel>() {
			private static final long serialVersionUID = 2141747588240960775L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Channel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (channel.getId()!=null && !"".equals(channel.getId())) {
					Predicate predicate = criteriaBuilder.equal(root.get("id").as(String.class), channel.getId());
					predicates.add(predicate);
				}
				if (channel.getName()!=null && !"".equals(channel.getName())) {
					Predicate predicate = criteriaBuilder.equal(root.get("name").as(String.class), channel.getName());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		});
	}
	
	@Override
	public List<Channel> searchPageable(Channel channel, int pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, ArticleConst.COUNT_PER_PAGE);
		//Specification用于封装查询条件
		return repository.findAll(new Specification<Channel>() {
			private static final long serialVersionUID = 2141747588240960775L;

			/** 
			 * @param root 封装要查询的对象
			 * @param query 封装查询关键字
			 * @param criteriaBuilder 封装查询的条件对象 
			 */
			@Override
			public Predicate toPredicate(Root<Channel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (channel.getId()!=null && !"".equals(channel.getId())) {
					Predicate predicate = criteriaBuilder.equal(root.get("id").as(String.class), channel.getId());
					predicates.add(predicate);
				}
				if (channel.getName()!=null && !"".equals(channel.getName())) {
					Predicate predicate = criteriaBuilder.equal(root.get("name").as(String.class), channel.getName());
					predicates.add(predicate);
				}
				Predicate[] preArr = new Predicate[predicates.size()];
				predicates.toArray(preArr);
				return criteriaBuilder.and(preArr);
			}
		}, pageRequest).getContent();
	}

	@Override
	public void delete(Channel channel) {
		repository.delete(channel);
	}

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

	@Override
	public void create(Channel channel) {
		channel.setId(String.valueOf(worker.nextId()));
		repository.save(channel);
	}

	@Override
	public void update(Channel channel) {
		repository.save(channel);
	}

	@Override
	public List<Channel> findAll() {
		return repository.findAll();
	}


}
