/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.zhihao.tensquare.entity.Spit;

/**
 * @author zzh
 * 2018年11月28日
 */
public interface SpitRepository extends MongoRepository<Spit, String> {

	Page<Spit> findByParentId(String id, Pageable pageable);
}
