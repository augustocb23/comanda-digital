package main.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException {
		//verifica se ainda é possível redirecionar
		if (response.isCommitted()) {
			System.out.println("Response already committed from server. Unable to redirect");
			return;
		}
		//salva os dados do erro
		HttpSession session = session();
		session.setAttribute("error", true);
		session.setAttribute("exception", exception);
		session.setAttribute("message", exception.getLocalizedMessage());
		//redireciona para a página de login
		redirectStrategy.sendRedirect(request, response, "/login");
	}
}
