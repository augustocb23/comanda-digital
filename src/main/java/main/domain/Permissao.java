package main.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "permissao")
public class Permissao extends AbstractEntity<Integer> implements GrantedAuthority {
	@Column(nullable = false, unique = true)
	public String nome;

	@Override
	public String getAuthority() {
		return nome;
	}
}
