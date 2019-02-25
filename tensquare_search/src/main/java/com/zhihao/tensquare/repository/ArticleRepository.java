/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zhihao.tensquare.entity.Article;

/**
 * @author zzh
 * 2018年11月30日
 */
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

	Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
