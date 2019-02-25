/**
 * 
 */
package com.zhihao.tensquare.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zzh
 * 2018年11月30日
 */
@Document(indexName = "tensquare_article", type="article")
@Getter
@Setter
@NoArgsConstructor
public class Article implements Serializable {

	private static final long serialVersionUID = -6953932153166114919L;

	@Id
	private String id;
	
	//是否索引，表示该域能否被搜索到
	//是否分词，表示搜索的时候是整体匹配还是单词匹配
	//是否存储，表示是否在页面上显示
	@Field(index=true, analyzer="ik_max_word", searchAnalyzer="ik_max_word")
	private String title;

	@Field(index=true, analyzer="ik_max_word", searchAnalyzer="ik_max_word")
	private String content;

	private String state;
}
