/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Problem;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface ProblemService {

	List<Problem> findAll();

	List<Problem> findAllByLabelId(String labelId, int pageNum);
	
	Problem findById(String id);
	
	List<Problem> search(Problem problem);

	List<Problem> searchPageable(Problem problem, int pageNum);

	List<Problem> findHotProblem(String labelId, int pageNum);

	List<Problem> findUnsolvedProblem(String labelId, int pageNum);

	List<Problem> findNewestProblem(String labelId, int pageNum);

	List<Problem> findWaitToAnswer(String labelId, int pageNum);

	boolean deleteById(String id);
	
	Long total();
	
	Long problemNumberForLabel(String labelId);
	
	void create(Problem problem);
	
	boolean update(Problem problem);

}
