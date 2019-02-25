/**
 * 
 */
package com.zhihao.tensquare.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author zzh 2018年12月6日
 */
public class PropertyCopyUtil {

	// 获取对象中属性值为null的属性名
	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	// 将src中不为null的属性值复制到target中，并返回target
	public static Object copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
		return target;
	}
}
