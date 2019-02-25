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
@Table(name="tb_user_label")
public class User_Label implements Serializable {
	
	private static final long serialVersionUID = 3921249349676506914L;
	
	@Id
	@Column(name="userid")
	private String userId;
	@Id
	@Column(name="labelid")
	private String labelId;

}
