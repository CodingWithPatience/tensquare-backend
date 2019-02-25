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
 * 2018年12月2日
 */
@Component
@Data
@NoArgsConstructor
@Entity
@Table(name="tb_admin")
public class Admin implements Serializable {
	
	private static final long serialVersionUID = -3926605685356353448L;

	@Id
	private String id;
	@Column(name="loginname")
	private String loginName;
	private String password;
	private String state;
}
