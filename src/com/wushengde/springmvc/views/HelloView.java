package com.wushengde.springmvc.views;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

//HelloView类实现了View接口 表示该HelloView类是一个视图
@Component
public class HelloView implements View {

	//表示返回内容类型：
	@Override
	public String getContentType() {

		return "text/html";
	}

	//渲染视图：
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			response.getWriter().write("hello view ,time:"+new Date());
	}
	
}
