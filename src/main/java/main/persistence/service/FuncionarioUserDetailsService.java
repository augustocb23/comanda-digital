package main.persistence.service;

import main.domain.Funcionario;
import main.domain.FuncionarioUserDetails;
import main.persistence.dao.FuncionarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioUserDetailsService implements UserDetailsService {
	private final FuncionarioDao funcionarios;

	@Autowired
	public FuncionarioUserDetailsService(FuncionarioDao funcionarios) {
		this.funcionarios = funcionarios;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		//busca o usuário
		Funcionario funcionario = funcionarios.findByLogin(login);
		if (funcionario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		return new FuncionarioUserDetails(funcionario);
	}
}
