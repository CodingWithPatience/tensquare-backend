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
@Table(name="tb_city")
public class City implements Serializable {

	private static final long serialVersionUID = 3540381964974774663L;

	@Id
	private String id;
	private String name;
	@Column(name="ishot")
	private String isHot;
}
