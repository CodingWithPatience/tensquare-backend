/**
 * 
 */
package com.zhihao.tensquare.constant;

/**
 * @author zzh
 * 2018年11月21日
 */
public class ArticleConst {

	//文章是否为热门文章
	public static final String TOP = "1";
	public static final String NOT_TOP = "0";
	
	//分页大小
	public static final Integer COUNT_PER_PAGE = 10;
	
	//redis中存储Article对象的key的前缀
	public static final String KEY_PREFFIX = "article_";

}
