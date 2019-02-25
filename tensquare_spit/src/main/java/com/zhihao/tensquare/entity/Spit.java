/**
 * 
 */
package com.zhihao.tensquare.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzh
 * 2018年11月28日
 */
@NoArgsConstructor
@Data
@Component
public class Spit implements Serializable {

	private static final long serialVersionUID = -4766256972153973766L;
	
	@Id
	private String _id;
	private String content;
	private Date publishTime;
	private String userId;
	private String nickName;
	private Integer visits;
	private Integer thumbUp;
	private Integer share;
	private Integer comment;
	private String state;
	private String parentId;
}
