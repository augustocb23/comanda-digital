package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Controller
public class LoginController extends HandlerInterceptorAdapter {
	@GetMapping("/")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

/*	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) throws
	Exception {
		//verifica a URL
		String uri = request.getRequestURI();
		if (uri.endsWith("loginForm") || uri.endsWith("efetuaLogin") || uri.contains("resources")) {
			return true;
		}
		//verifica se está logado
		if (request.getSession().getAttribute("logado") != null) {
			return true;
		}
		//salva a URL atual e redireciona
		request.getSession().setAttribute("location", uri);
		response.sendRedirect("login");
		return false;
	}*/
}
