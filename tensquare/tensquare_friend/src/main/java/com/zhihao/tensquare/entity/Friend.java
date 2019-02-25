/**
 * 
 */
package com.zhihao.tensquare.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzh
 * 2018年12月4日
 */
@Component
@NoArgsConstructor
@Data
@Entity
@Table(name="tb_friend")
@IdClass(value = Friend.class)
public class Friend implements Serializable {

	private static final long serialVersionUID = -9145853798762029550L;

	@Id
	@Column(name="userid")
	private String userId;
	@Id
	@Column(name="friendid")
	private String friendId;
	@Column(name="islike")
	private String isLike;
}
