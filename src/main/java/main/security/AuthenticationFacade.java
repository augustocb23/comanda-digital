package main.security;

import main.domain.Funcionario;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
	Authentication getAuthentication();

	Funcionario getFuncionario();

	boolean isAuthenticated();
}
