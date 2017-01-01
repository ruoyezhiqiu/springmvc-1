package com.wushengde.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {
	
	private static final String SUCCESS="success";
	
	
	/**
	 * 一下四个方法：测试Rest风格的URL
	 * 	以CRUD为例：
	 * 	新增：/order   POST
	 * 	修改：/order/1 PUT
	 * 	获取：/order/1 GET 
	 *  删除：/order/1 DELETE
	 *  
	 *   如何发送PUT 请求 和 DELETE 请求：
	 *   	1.需要在web.xml中配置 HiddenHttpMethodFilter 过滤器
	 *   	2.需要发送 POST 请求
	 *   	3.需要在发送 POST 请求时携带一个 name="_method" 的隐藏域，值为DELETE 或 PUT 
	 *   		即：	1.<input type="hidden" name="_method" value="DELETE"/>
	 *   			2.<input type="hidden" name="_method" value="PUT"/>
	 *   
	 *   在Springmvc的目标方法中如何获得id呢？
	 *   使用 @PathVariable 注解即可获得 URL 中的 id
	 */
	@RequestMapping(value="/testRest/{id}",method=RequestMethod.PUT)
	public String testRestPut(@PathVariable("id") Integer id){
		System.out.println("Test Rest Put"+id);
		return SUCCESS;
	}
	
	
	@RequestMapping(value="/testRest/{id}",method=RequestMethod.DELETE)
	public String testRestDelete(@PathVariable("id") Integer id){
		System.out.println("Test Rest Delete"+id);
		return SUCCESS;
	}
	
	
	@RequestMapping(value="/testRest",method=RequestMethod.POST)
	public String testRest(){
		System.out.println("Test Rest Post");
		return SUCCESS;
	}
	
	
	@RequestMapping(value="/testRest/{id}",method=RequestMethod.GET)
	public String testRest(@PathVariable("id") Integer id){
		System.out.println("Test Rest Get"+id);
		return SUCCESS;
	}
	
	
	/**
	 * @PathVariable注解：可以来映射URL中的占位符到目标方法的参数中
	 */
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id){
		System.out.println("testPathVariable method with a "+"param: "+id);
		return SUCCESS;
	}
	
	
	/**
	 * URL支持ANT风格的通配符：
	 * 						1.?:问号 表示支持单个任意字符 
	 * 						2.*:一颗星 表示支持多个任意字符  
	 * 						3.**:两颗星 表示匹配多层路径
	 */
	@RequestMapping("/testAntPath/*/abc")
	public String testAntPath(){
		System.out.println("testAntPath");
		return SUCCESS;
	}
	
	
	/**
	 * 此方法中：	1.params属性指定的条件是：请求中必须包含：username参数，且如果包含age参数则其值不能为10,不包含age也行！
	 * 			2.headers属性指定的条件：请求头信息中的Accept-Language必须等于："zh-CN,zh;q=0.8"
	 */
	@RequestMapping(value="/testParamsAndHeaders",params={"username","age!=10"},headers={"Accept-Language=zh-CN,zh;q=0.8"})
	public String testParamsAndHeaders(){
		System.out.println("testParamsAndHeaders");
		return SUCCESS;
	}
	
	
	/**
	 *	@RequestMapping注解的常用属性:
	 *							1.value(必须指定):表示请求的URL
	 *							2.method: 指定请求的方式
	 *							3.params:指定请求需要满足的参数条件
	 *							4.headers:表示请求头信息必须要满足的条件
	 *							
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
