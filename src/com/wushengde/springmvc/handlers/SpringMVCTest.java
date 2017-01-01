package com.wushengde.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {
	
	private static final String SUCCESS="success";
	
	/**
	 * 使用method属性来指定请求的方式
	 */
	@RequestMapping(value="/testMethod",method=RequestMethod.POST)
	public String testMethod(){
		System.out.println("testMethod");
		return SUCCESS;
	}
	
	/**
	 * 1.@RequestMapping注解：除了修饰方法，还可以修饰类
	 * 2.
	 * 	a.类定义处@RequestMapping注解：提供初步的请求映射信息。相对于 WEB 应用的根目录
		b.方法处@RequestMapping注解：提供进一步的细分映射信息。相对于类定义处的 URL。若 
		类定义处未标注 @RequestMapping，则方法处标记的 URL 相对于WEB 应用的根目录
	 * 
	 */
	@RequestMapping("/testRequestMapping")
	public String testRequestMapping(){
		System.out.println("testRequestMapping");
		return SUCCESS;
	}
}
