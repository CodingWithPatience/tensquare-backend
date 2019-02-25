/**
 * 
 */
package com.zhihao.tensquare.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzh
 * 2018年11月21日
 */
@NoArgsConstructor
@Data
@Component
@Entity
@Table(name="tb_problem_label")
public class Problem_Label implements Serializable {
	
	private static final long serialVersionUID = 6321879827540532069L;
	
	@Id
	@Column(name="problemid")
	private String problemId;
	@Id
	@Column(name="labelid")
	private String labelId;

}
