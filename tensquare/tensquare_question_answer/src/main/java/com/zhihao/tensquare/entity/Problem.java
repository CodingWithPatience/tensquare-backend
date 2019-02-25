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
@Table(name="tb_problem")
public class Problem implements Serializable {
	
	private static final long serialVersionUID = -5958696351299065407L;
	
	@Id
	private String id;
	private String title;
	private String content;
	@Column(name="createtime")
	private Date createTime;
	@Column(name="updatetime")
	private Date updateTime;
	@Column(name="userid")
	private String userId;
	private String nickname;        //提问人昵称
	private Long visits;            //浏览数量
	@Column(name="thumbup")
	private Long thumbUp;              //点赞数量
	@Column(name="replynumber")
	private Long replyNumber;       //回复数量
	private String solve;           //问题是否已经解决
	@Column(name="replyname")
	private String replyName;       //回复人名称
	@Column(name="replytime")
	private Date replyTime;         //最新回复时间

}
