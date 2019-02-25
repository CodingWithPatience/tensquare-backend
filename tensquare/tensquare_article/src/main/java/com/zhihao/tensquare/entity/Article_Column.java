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
@Table(name="tb_column")
public class Article_Column implements Serializable {
	
	private static final long serialVersionUID = -3228003134273454421L;
	
	@Id
	private String id;
	private String name;
	private String summary;
	@Column(name="userid")
	private String userId;
	@Column(name="createtime")
	private Date createTime;
	@Column(name="checktime")
	private Date checkTime;
	private String state;

}
