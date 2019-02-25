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
@Table(name="tb_reply")
public class Reply implements Serializable {
	
	private static final long serialVersionUID = -6886006070188869283L;
	
	@Id
	private String id;
	@Column(name="problemid")
	private String problemId;
	private String content;
	@Column(name="createtime")
	private Date createTime;
	@Column(name="updatetime")
	private Date updateTime;
	@Column(name="userid")
	private String userId;
	private String nickname;

}
