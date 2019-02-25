/**
 * 
 */
package com.zhihao.tensquare.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author zzh
 * 2018年11月8日
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 4110262000778032144L;

	private boolean flag;
	
	private Integer code;
	
	private String message;

	private T data;
	
	public Result(boolean flag, Integer code) {
		super();
		this.flag = flag;
		this.code = code;
	}
	
	public Result (boolean flag, Integer code, T data) {
		super();
		this.flag = flag;
		this.code = code;
		this.data = data;
	}

	public Result(boolean flag, Integer code, String message) {
		super();
		this.flag = flag;
		this.code = code;
		this.message = message;
	}
	
}
