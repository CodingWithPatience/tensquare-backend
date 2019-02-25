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
@Table(name="tb_gathering")
public class Gathering implements Serializable {

	private static final long serialVersionUID = -7227297092661837360L;
	
	@Id
	private String id;
	private String name;
	private String summary;
	private String detail;
	private String sponsor;
	private String image;
	@Column(name="starttime")
	private Date startTime;
	@Column(name="endtime")
	private Date endTime;
	private String address;
	@Column(name="enrolltime")
	private Date enrollTime;
	private String state;
	@Column(name="cityid")
	private String cityId;
}
