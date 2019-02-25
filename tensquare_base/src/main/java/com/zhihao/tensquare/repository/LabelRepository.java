/**
 * 
 */
package com.zhihao.tensquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zhihao.tensquare.entity.Label;

import java.util.List;

/**
 * @author zzh
 * 2018年11月19日
 */
@Repository
public interface LabelRepository extends JpaRepository<Label, String>,
		JpaSpecificationExecutor<Label> {

    List<Label> findByState(String state);
}
