/**
 * 
 */
package com.zhihao.tensquare.entity;

import java.io.Serializable;

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
@Table(name="tb_channel")
public class Channel implements Serializable {

	private static final long serialVersionUID = -4155636379825943345L;
	
	@Id
	private String id;
	private String name;
	private String state;

}
