package com.how2java.springboot.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.Date;

@Controller
public class HelloController {

	@RequestMapping("/index")
	public String hello(Model m) throws Exception {
		m.addAttribute("msg", DateFormat.getDateTimeInstance().format(new Date()));
		return "index";
	}

	@RequestMapping("/add")
	public String add() throws Exception {
		return "user/add";
	}

	@RequestMapping("/update")
	public String update() throws Exception {
		return "user/update";
	}

	@RequestMapping("/login")
	public String login(Model model, String name, String pwd) throws Exception {
		Subject               subject = SecurityUtils.getSubject();
		UsernamePasswordToken token   = new UsernamePasswordToken(name, pwd);
		try {
			subject.login(token);
			return "redirect:/index";
		} catch (UnknownAccountException e) {
			model.addAttribute("msg", "用户名不存在！");
		} catch (IncorrectCredentialsException e) {
			model.addAttribute("msg", "密码错误！");
		}
		return "/login";
	}

	@RequestMapping("/to_login")
	public String toLogin() throws Exception {
		return "/login";
	}

	@RequestMapping("/noauth")
	public String noauth() throws Exception {
		return "/noauth";
	}
}
