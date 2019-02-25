/**
 * 
 */
package com.zhihao.tensquare.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zhihao.tensquare.entity.Article;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface ArticleRepository extends JpaRepository<Article, String>,
		JpaSpecificationExecutor<Article> {

	@Modifying
	@Query(value="update tb_article set state='1' where id=?", nativeQuery=true)
	int updateState(String id);
	
	@Modifying
	@Query(value="update tb_article set like=like+1 where id=?", nativeQuery=true)
	int addLike(String id);

	List<Article> findByIsTop(String top);

	Page<Article> findByChannelId(String channelId, Pageable pageable);

	Page<Article> findByColumnId(String columnId, Pageable pageable);
	
	Article findByIdAndUserId(String id, String userId);
	
	@Modifying
	@Query(value="delete from tb_article where id=?1 and userid=?2", nativeQuery=true)
	int deleteByIdAndUserId(String id, String userId);
}
