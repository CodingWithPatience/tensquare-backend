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
 * 2018年12月2日
 */
@Component
@Data
@NoArgsConstructor
@Entity
@Table(name="tb_follow")
@IdClass(Follow.class)
public class Follow implements Serializable {

	private static final long serialVersionUID = 2990711685212308560L;

	@Id
	@Column(name="userid")
	private String userId;
	@Id
	@Column(name="targetid")
	private String targetId;
}
