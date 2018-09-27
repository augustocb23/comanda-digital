package main.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
										Authentication authentication) throws IOException {
		//verifica se ainda é possível redirecionar
		if (httpServletResponse.isCommitted()) {
			System.out.println("Response already committed from server. Unable to redirect");
			return;
		}
		//testa se é o usuário admin
		String login = String.valueOf(authentication.getPrincipal());
		if (login.contentEquals("admin"))
			//redireciona para a página Usuários
			redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/funcionarios/admin");
		else
			//redireciona para a página inicial
			redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");

		System.out.println("Usuário conectado: " + authentication.getPrincipal());
	}
}
