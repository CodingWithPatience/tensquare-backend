/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tensquare.constant.SpitConst;
import com.zhihao.tensquare.entity.Spit;
import com.zhihao.tensquare.repository.SpitRepository;
import com.zhihao.tensquare.service.SpitService;
import com.zhihao.tensquare.util.IdWorker;

/**
 * @author zzh
 * 2018年11月28日
 */
@Service
@Transactional
public class SpitServiceImpl implements SpitService {
	
	@Autowired
	private SpitRepository repository;
	@Autowired
	private IdWorker worker;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	public List<Spit> findAll() {
		return repository.findAll();
	}

	@Override
	public Spit findById(String id) {
		Optional<Spit> optional = repository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<Spit> findByParentId(String parentId, int pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, SpitConst.COUNT_PER_PAGE);
		return repository.findByParentId(parentId, pageRequest).getContent();
	}

	@Override
	public List<Spit> search(Spit spit) {
		return repository.findAll(Example.of(spit));
	}

	@Override
	public List<Spit> searchPageable(Spit spit, int pageNum) {
		return repository.findAll(Example.of(spit),
				PageRequest.of(pageNum-1, SpitConst.COUNT_PER_PAGE)).getContent();
	}

	@Override
	public void update(Spit spit) {
		repository.save(spit);
	}

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

	@Override
	public void create(Spit spit) {
		spit = initSpit(spit);
		if (spit.getParentId()!=null && !"".equals(spit.getParentId())) {
			incCommentCount(spit.getParentId());
		}
		repository.save(spit);
	}

	@Override
	public boolean like(String id, String userId) {
		if (!isThumbUp(userId)) {
			Query query=new Query();
			query.addCriteria(Criteria.where("_id").is(id));
			Update update=new Update();
			update.inc("thumbUp",1);
			mongoTemplate.updateFirst(query,update,"spit");
			//用户点赞成功，在redis中记录用户已经点赞过
			redisTemplate.opsForValue().set(SpitConst.KEY_PREFFIX+userId, userId);
			return true;
		}
		return false;     //重复点赞
	}
	
	private Spit initSpit(Spit spit) {
		String id = String.valueOf(worker.nextId());
		spit.set_id(id);
		spit.setPublishTime(new Date());
		spit.setVisits(0);
		spit.setShare(0);
		spit.setThumbUp(0);
		spit.setComment(0);
		spit.setState("1");
		return spit;
	}
	
	private void incCommentCount(String id) {
		Query query=new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update=new Update();
		update.inc("comment",1);
		mongoTemplate.updateFirst(query,update,"spit");
	}

	private boolean isThumbUp(String userId) {
		if (redisTemplate.opsForValue().get(SpitConst.KEY_PREFFIX+userId)!=null) {
			return true;
		}
		return false;
	}
}
