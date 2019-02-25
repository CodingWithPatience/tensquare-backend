/**
 * 
 */
package com.zhihao.tensquare.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author zzh
 * 2018年12月9日
 */
@Component
public class WebFilter extends ZuulFilter {

	@Override
	public Object run() throws ZuulException {
		System.out.println("进入webzuul过滤器");
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		String header = request.getHeader("Authorization");
		if (header!=null && !"".equals(header)) {
			context.addZuulRequestHeader("Authorization", header);
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
