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
 * 2018年12月2日
 */
@Component
@Data
@NoArgsConstructor
@Entity
@Table(name="tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 566951989893120650L;
	
	@Id
	private String id;
	@Column(name="loginname")
	private String loginName;
	private String password;
	private String mobile;
	@Column(name="nickname")
	private String nickName;
	private String sex;
	private Date birthday;
	private String image;           //头像
	private String email;
	@Column(name="registerdate")
	private Date registerDate;
	@Column(name="updatedate")
	private Date updateDate;
	@Column(name="lastlogin")
	private Date lastLogin;         //最后登陆时间
	@Column(name="onlinetime")
	private Long onlineTime;        //在线时间，单位：分钟
	private String interest;
	private String personality;
	@Column(name="fanscount")
	private Integer fansCount;
	@Column(name="followcount")
	private Integer followCount;

}
