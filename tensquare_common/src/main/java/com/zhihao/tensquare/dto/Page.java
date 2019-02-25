package com.zhihao.tensquare.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Page<T> implements Serializable {
	private static final long serialVersionUID = 6039747728305307641L;
	
	private long total;          //数据总数
    private List<T> rows;        //每一页包含的数据量

}
