package com.wushengde.springmvc.handlers;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.wushengde.springmvc.entities.User;

//@SessionAttributes注解：表示将目标方法中的模型属性数据添加到session域中，方便多个请求共享数据
//@SessionAttributes中的属性：1.value：表示要放入session域中的键；2.types表示要放入session域中的值的类型
//@SessionAttributes：表示将user键以及值为String类型的模型数据放到Session域中
@SessionAttributes(value={"user"},types={String.class})
@RequestMapping("/springmvc")
@Controller
public class SpringMVCTest {
	
	private static final String SUCCESS="success";
	
	//测试重定向：如果测试转发只需要将下面的redirect改为：forward
	@RequestMapping("/testRedirect")
	public String testRedirect(){
		System.out.println("Text Redirect");
		return "redirect:/index.jsp";
	}
	
	
	
	//测试自定义视图：
	@RequestMapping("/testView")
	public String testView(){
		System.out.println("Test View");
		return "helloView";
	}
	
	
	@RequestMapping("/testViewAndViewResolver")
	public String testViewAndViewResolver(){
		System.out.println("testViewAndViewResolver");
		return SUCCESS;
	}
	
	
	/**
	 * 1.@ModelAttribute 注解：带有该注解的方法，都会在每一个目标方法执行之前被springmvc 调用！
	 * 2.@ModelAttribute注解：也可以用来修饰目标方法POJO类型的入参，其value属性值有以下作用：
	 * 		1).SpringMVC 会使用value属性值在implicitModel 中查找对应的对象，若存在则会直接传入到目标方法的入参中。
	 * 		2).SpringMVC 会value为key，POJO 类型的对象为value,存入到request中。
	 */
	//在执行任意一个目标方法的时候都会先执行该方法！
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false) Integer id,Map<String,Object> map){
		System.out.println("@ModelAttribute method");
		if(id!=null){
			//模拟从数据库中获取对象
			User user=new User(1, "Tom", "123456", "tom@atguigu.com", 12);
			System.out.println("从数据库中获取一个对象"+user);
			map.put("user", user);
		}
	}
	
	
	@RequestMapping("/testModelAttribute")
	public String testModelAttribute(User user){
		System.out.println("修改："+user);
		return SUCCESS;
	}
	
	
	/**
	 * 开发过程要用到：@SessionAttributes 注解，属于常用注解
	 * 
	 * @ SessionAttributes注解放到目标方法所在类上，且该注解只能放到类上边，不能放到方法上边。
	 * 	作用： 除了可以通过属性名指定需要放到会 话中的属性外(实际上使用其value属性)，
	 * 	还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中(实际上使用其tpyes属性)
	 */
	@RequestMapping("/testSessionAttributes")
	public String testSessionAttributes(Map<String , Object> map){
		User user=new User("Tom", "123456", "tom@atguigu.com", 12);
		map.put("user", user);
		map.put("school", "atguigu");
		return SUCCESS;
	}
	
	
	/**
	 *  目标方法可以添加Map 类型(也可以是：Model 类型跟ModelMap 类型)的参数
	 *  Springmvc会把Map类型的数据放到request请求域中
	 */
	@RequestMapping("/testMap")
	 public String testMap(Map<String, Object> map){
		System.out.println(map.getClass().getName());
		 map.put("names",Arrays.asList("Tom","Jerry","Mike"));
		 return SUCCESS;
	 }
	
	
	/**
	 * 	目标方法的返回值除了String类型的success，还可以是：ModelAndView 类型
	 * 	其中可以包含：视图、模型信息	
	 * 	Springmvc会把ModelAndView中的 model 中的数据放到request 的请求 域对象 中。
	 */
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView(){
		String viewName=SUCCESS;
		//利用ModelAndView的构造函数配置视图信息
		ModelAndView modelAndView=new ModelAndView(viewName);
		//添加模型数据到ModelAndView 中
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}
	
	
	/**
	 * 可以使用Servlet原生的API作为目标方法的参数：
	 * 具体支持以下类型的参数： 
	 * 		1.HttpServletRequest 
	 *		2.HttpServletResponse 
	 *		3.HttpSession 
	 *		4.java.security.Principal 
	 *		5.Locale 
	 *		6.InputStream 
	 *		7.OutputStream 
	 *		8.Reader 
	 *		9.Writer 
	 * @throws IOException 
	 */
	@RequestMapping("/testServletAPI")
	public void testServletAPI(HttpServletRequest request,HttpServletResponse response,Writer out) throws IOException{
		System.out.println("TestServletAPI,"+request+","+response);
		out.write("Test Servlet Source API");
		//return SUCCESS;
	}
	
	
	/**
	 * 	Spring MVC 会按请求参数名和 POJO 属性名进行自动匹配，自动为该对象填充属性值。支持级联属性。
	 *	如：dept.deptId、dept.address.tel 等
	 *	以下例子中：address.province、address.city属于级联属性
	 * 
	 */
	@RequestMapping("/testPojo")
	public String testPojo(User user){
		System.out.println("testPojo: "+user);
		return SUCCESS;
	}
	
	
	/**
	 * @CookieValue:映射一个cookie值，属性配置同@RequestHeader，@RequestParam
	 */
	@RequestMapping("/testCookieValue")
	public String testCookieValue(@CookieValue("JSESSIONID") String sessionId){
		System.out.println("Test CookieValue:sessionId:" +sessionId);
		return SUCCESS;
	}
	
	
	/**
	 * @RequestHeader用法同@RequestParam一样，作用：映射请求头信息，不常用
	 */
	@RequestMapping("/testRequestHeader")
	public String testRequestHeader(@RequestHeader(value="Accept-Language") String al){
		System.out.println("Test RequestHeader,Accept-Language: "+al);
		return SUCCESS;
	}
	
	
	/**
	 * @RequestParam 注解：来映射请求参数。
	 * 	value属性 值：为请求参数的参数名
	 * 	required属性：该参数是否必须传入，默认为：true
	 * 	defaultValue属性：请求参数的默认值
	 */
	@RequestMapping(value="/testRequestParam")
	public String testRequestParam(@RequestParam(value="username") String un,
			@RequestParam(value="age",required=false,defaultValue="null") Integer age){
		System.out.println("Test RequestParam with params username : "+un+" age: "+age);
		return SUCCESS;
	}
	
	
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
