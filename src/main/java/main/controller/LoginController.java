package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Controller
public class LoginController extends HandlerInterceptorAdapter {
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
}
