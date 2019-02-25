/**
 *
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhihao.tensquare.entity.Problem;

/**
 * @author zzh
 * 2018年11月20日
 */
public interface ProblemRepository extends JpaRepository<Problem, String>,
		JpaSpecificationExecutor<Problem> {

	@Query("select p from Problem p where p.id in (select problemId from Problem_Label" +
			" where labelId=:labelId) order by p.replyTime desc")
	Page<Problem> findNewestByLabelId(@Param(value="labelId") String labelId, Pageable pageable);

	@Query("select p from Problem p where p.id in (select problemId from Problem_Label" +
			" where labelId=:labelId) order by p.replyNumber desc")
	Page<Problem> findHotByLabelId(@Param(value="labelId") String labelId, Pageable pageable);

	@Query("select p from Problem p where p.id in (select problemId from Problem_Label" +
			" where labelId=:labelId) and p.solve='0' order by p.createTime desc")
	Page<Problem> findUnsolvedByLabelId(@Param(value="labelId") String labelId, Pageable pageable);

	@Query("select count(*) from Problem p where p.id in (select problemId from Problem_Label" +
			" where labelId=:labelId)")
	Long problemNumberForLabel(@Param(value="labelId") String labelId);

	@Query("select p from Problem p where p.id in (select problemId from Problem_Label" +
			" where labelId=:labelId) order by p.updateTime desc")
	Page<Problem> findByLabelId(@Param(value="labelId") String labelId, Pageable pageable);

	@Query(value="select * from tb_problem where id in (select problemid from tb_problem_label" +
			" where labelid=?) and replynumber=0 order by updatetime desc", nativeQuery=true)
	Page<Problem> findWaitToAnswer(String labelId, Pageable pageable);

	Problem findByIdAndUserId(String id, String userId);

	@Modifying
	@Query(value="delete from tb_problem where id = ?1 and userid = ?2", nativeQuery=true)
	int deleteByIdAndUserId(String id, String userId);

	@Query(value="select * from tb_problem where replynumber=0 order by updatetime desc", nativeQuery=true)
	Page<Problem> findWait(Pageable pageable);

	@Query("select p from Problem p order by p.replyNumber desc")
	Page<Problem> findHot(Pageable pageable);

	@Query("select p from Problem p order by p.replyTime desc")
	Page<Problem> findNewest(Pageable pageable);

	@Query("select p from Problem p where p.solve='0' order by p.createTime desc")
	Page<Problem> findUnsolved(Pageable pageable);
}
