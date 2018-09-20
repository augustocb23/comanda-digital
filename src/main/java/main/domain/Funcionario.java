package main.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionario")
public class Funcionario extends AbstractEntity<Integer> {
	@NotBlank(message = "Insira o login do usuário")
	@Size(min = 5, message = "Mínimo de {min} caracteres")
	@Column(nullable = false, unique = true)
	private String login;
	@NotBlank(message = "Insira uma senha")
	@Column(nullable = false, length = 60, columnDefinition = "CHAR(60)")
	private String senha;
	@NotBlank(message = "Insira o nome do Funcionário")
	@Size(min = 5, message = "Mínimo de {min} caracteres")
	@Column(nullable = false)
	private String nome;
	@NotNull(message = "Informe o grupo do funcionário")
	@ManyToOne
	@JoinColumn(name = "grupo", nullable = false)
	private Grupo grupo;
	@Column(nullable = false, columnDefinition = "BIT(1) DEFAULT TRUE")
	private Boolean ativo = true;

	@Override
	public String toString() {
		return getNome();
	}

	Boolean isAtivo() {
		return ativo;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = (new BCryptPasswordEncoder()).encode(senha);
	}

	public List<Autorizacao> getAutorizacoes() {
		return grupo.getAutorizacoes();
	}

	public Boolean getAtivo() {
		return isAtivo();
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getLogin() {
		return login;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getNome() {
		return nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setNome(String string) {
		nome = string;
	}


}
