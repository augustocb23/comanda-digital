package main.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class FuncionarioUserDetails implements UserDetails {
	private Funcionario funcionario;

	public FuncionarioUserDetails(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public String toString() {
		return getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return funcionario.getPermissoes();
	}

	@Override
	public String getPassword() {
		return funcionario.getSenha();
	}

	@Override
	public String getUsername() {
		return funcionario.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return funcionario.isAtivo();
	}

	@Override
	public boolean isAccountNonLocked() {
		return funcionario.isAtivo();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return funcionario.isAtivo();
	}

	@Override
	public boolean isEnabled() {
		return funcionario.isAtivo();
	}
}
