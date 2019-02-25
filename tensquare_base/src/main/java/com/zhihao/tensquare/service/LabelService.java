/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Label;

/**
 * @author zzh
 * 2018年11月19日
 */
public interface LabelService {

	List<Label> findAll();

	List<Label> topList();

	Label findById(String id);
	
	List<Label> search(Label label);
	
	void delete(Label label);
	
	void deleteById(String id);
	
	Long total();
	
	void create(Label label);
	
	void update(Label label);
}
