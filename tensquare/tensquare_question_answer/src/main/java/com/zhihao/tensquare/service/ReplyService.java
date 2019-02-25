/**
 * 
 */
package com.zhihao.tensquare.service;

import java.util.List;

import com.zhihao.tensquare.entity.Reply;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface ReplyService {

	List<Reply> findByProblemId(String problemId, int pageNum);
	
	List<Reply> findByUserId(String userId, int pageNum);
	
	List<Reply> search(Reply reply, int pageNum);

	Reply findById(String id);
	
	boolean deleteById(String id);
	
	Long replyNumberForProblem(String problemId);

	Long replyNumberForUser(String userId);

	Long replyNumberForUserAndProblem(String problemId, String userId);
	
	void create(Reply reply);
	
	boolean update(Reply reply);

}
