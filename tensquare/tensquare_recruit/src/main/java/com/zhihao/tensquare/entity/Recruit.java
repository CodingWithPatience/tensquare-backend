/**
 * 
 */
package com.zhihao.tensquare.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzh
 * 2018年11月20日
 */
@NoArgsConstructor
@Data
@Component
@Entity
@Table(name="tb_recruit")
public class Recruit implements Serializable {

	private static final long serialVersionUID = 7831944597380085271L;

	@Id
	private String id;
	@Column(name="jobname")
	private String jobName;
	private String salary;
	private String condition;            //经验要求
	private String education;            //学历要求
	private String type;                 //任职方式
	private String address;   
	private String eid;                  //企业id
	private String state;                //状态，0:关闭；1:开启；2:推荐
	private String url;
	private String label;
	private String description;          //职位描述
	private String demand;               //职位要求
	@Column(name="createtime")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
}
