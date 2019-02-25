/**
 * 
 */
package com.zhihao.tensquare.filter;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zhihao.tensquare.util.JwtUtil;

import io.jsonwebtoken.Claims;

/**
 * @author zzh
 * 2018年12月9日
 */
@Component
public class WebFilter extends ZuulFilter {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Object run() throws ZuulException {
		System.out.println("进入managerzuul过滤器");
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		
		//OPTIONS:zuul内部方法，查找对应的服务
		//return null，直接放行
		if (request.getMethod().equals("OPTIONS")) {
			return null;
		}
		//请求路径中含有login，是登陆操作，直接放行
		if (request.getRequestURI().indexOf("login")>0) {
			return null;
		}
		//开始验证是否具有管理员权限，如果没有终止操作
		String header = request.getHeader("Authorization");
		if (header!=null && !"".equals(header)) {
			if (header.startsWith("mytoken_")) {
				String token = header.substring(8);
				try {
					Claims claims = jwtUtil.parseJWT(token);
					if (claims!=null && claims.get("roles").equals("admin")) {
						System.out.println("权限验证通过");
						//转发请求头部信息
						context.addZuulRequestHeader("Authorization", header);
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();
					//token解析出现异常，终止操作
					context.setSendZuulResponse(false);
				}
			}
			context.addZuulRequestHeader("Authorization", header);
		}
		//token验证不通过，终止操作
		context.setSendZuulResponse(false);
		//设置返回信息
		context.setResponseStatusCode(403);    //http状态码
		context.setResponseBody("无权访问");
		context.getResponse().setContentType("text/html;charset=UTF‐8");
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;      //是否开启过滤器
	}

	@Override
	public int filterOrder() {
		return 0;         //过滤器优先级
	}

	@Override
	public String filterType() {
		return "pre";     //过滤器类型，pre：前置过滤器；post：后置过滤器
	}

}
