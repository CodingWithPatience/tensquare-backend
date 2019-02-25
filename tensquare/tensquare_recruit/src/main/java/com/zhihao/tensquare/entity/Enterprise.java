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
 * 2018年11月20日
 */
@NoArgsConstructor
@Data
@Component
@Entity
@Table(name="tb_enterprise")
public class Enterprise implements Serializable {

	private static final long serialVersionUID = 1014658936268580092L;
	
	@Id
	private String id;
	private String name;
	private String summary;           //企业简介
	private String address;
	private String labels;
	private String coordinate;        //坐标
	@Column(name="ishot")
	private String isHot;             //1:热门；0:非热门
	private String logo;
	private String url;               //网址
	@Column(name="jobcount")
	private Integer jobCount;

}
