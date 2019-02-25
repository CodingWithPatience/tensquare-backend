/**
 * 
 */
package com.zhihao.tensquare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zhihao.tensquare.constant.SearchConst;
import com.zhihao.tensquare.entity.Article;
import com.zhihao.tensquare.repository.ArticleRepository;
import com.zhihao.tensquare.service.ArticleService;
import com.zhihao.tensquare.util.IdWorker;

/**
 * @author zzh
 * 2018年11月30日
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private IdWorker worker;
	
	@Override
	public void save(Article article) {
		article.setId(worker.nextId()+"");
		articleRepository.save(article);
	}

	
	@Override
	public List<Article> findByKeyword(String keyword, int pageNum) {
		pageNum = pageNum < 1 ? 1 : pageNum;
		PageRequest pageRequest = PageRequest.of(pageNum-1, SearchConst.ARTICLE_PAGE_SIZE);
		return articleRepository.findByTitleOrContentLike(keyword, keyword, pageRequest).getContent();
	}

	
}
