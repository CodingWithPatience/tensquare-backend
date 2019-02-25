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
 * 2018年11月26日
 */
@NoArgsConstructor
@Data
@Component
@Entity
@Table(name="tb_article")
public class Article implements Serializable {

	private static final long serialVersionUID = 3621751967771395852L;

	@Id
	private String id;
	@Column(name="columnid")
	private String columnId;
	@Column(name="userid")
	private String userId;
	private String title;
	private String content;
	private String image;
	@Column(name="createtime")
	private Date createTime;
	@Column(name="updatetime")
	private Date updateTime;
	@Column(name="ispublic")
	private String isPublic;
	@Column(name="istop")
	private String isTop;
	private int visits;
	@Column(name="thumbup")
	private int thumbUp;
	private int comment;
	private String state;
	@Column(name="channelid")
	private String channelId;
	private String url;
	private String type;

}
