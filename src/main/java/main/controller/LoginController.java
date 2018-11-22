package main.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends HandlerInterceptorAdapter {
	@GetMapping("/login")
	public String login(ModelMap model, @RequestParam(required = false) String error) {
		//redireciona para a página inicial se já estiver autenticado (não é anônimo)
		String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if (!user.contentEquals("anonymousUser"))
			return "redirect:/";
		//retorna o template
		model.addAttribute("error", error);
		return "login";
	}

	@GetMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		model.addAttribute("logout", true);
		return "redirect:/login";
	}
}
