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
 * 2018年11月19日
 */
@NoArgsConstructor
@Data
@Component
@Entity
@Table(name="tb_label")
public class Label implements Serializable {
	
	private static final long serialVersionUID = 980392428321635138L;
	
	@Id
	private String id;
	@Column(name="labelname")
	private String labelName;
	private String state;
	private String recommend;
	private Long count;
	private Long fans;
}
