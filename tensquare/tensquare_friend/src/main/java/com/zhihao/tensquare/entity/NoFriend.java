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
@Table(name="tb_nofriend")
@IdClass(value = NoFriend.class)
public class NoFriend implements Serializable {

	private static final long serialVersionUID = -495638335058392200L;

	@Id
	@Column(name="userid")
	private String userId;
	@Id
	@Column(name="friendid")
	private String friendId;
}
