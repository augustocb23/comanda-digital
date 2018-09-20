package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Controller
public class LoginController extends HandlerInterceptorAdapter {
	@GetMapping("/login")
	public ModelAndView login(@RequestParam(name = "error", required = false) String error) {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("error", error != null);
		return modelAndView;
	}
}
