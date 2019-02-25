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
 * 2018年11月27日
 */
@NoArgsConstructor
@Data
@Component
@Entity
@Table(name="tb_user_gathering")
public class User_Gathering implements Serializable {

	private static final long serialVersionUID = -4276471917219458923L;
	
	@Id
	@Column(name="userid")
	private String userId;
	@Id
	@Column(name="gatheringid")
	private String gatheringId;
	@Column(name="exetime")
	private Date exeTime;
}
