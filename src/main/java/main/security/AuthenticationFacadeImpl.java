package main.security;

import main.domain.Funcionario;
import main.domain.FuncionarioUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {
	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public Funcionario getFuncionario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		FuncionarioUserDetails userDetails = (FuncionarioUserDetails) authentication.getPrincipal();
		return userDetails.getFuncionario();
	}
}