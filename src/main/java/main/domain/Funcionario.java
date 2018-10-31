package main.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionario")
public class Funcionario extends AbstractEntity<Long> {
	@Column(nullable = false, length = 60, columnDefinition = "CHAR(60)")
	@JsonIgnore
	protected String senha;
	@NotBlank(message = "Insira o login do usuário")
	@Size(min = 5, message = "Mínimo de {min} caracteres")
	@Column(nullable = false, unique = true)
	private String login;
	@NotBlank(message = "Insira o nome do Funcionário")
	@Size(min = 5, message = "Mínimo de {min} caracteres")
	@Column(nullable = false)
	private String nome;
	@NotNull(message = "Informe o grupo do funcionário")
	@ManyToOne
	@JoinColumn(name = "grupo", nullable = false)
	@JsonIgnore
	private Grupo grupo;
	@Column(nullable = false, columnDefinition = "BIT(1) DEFAULT TRUE")
	@JsonIgnore
	private boolean ativo = true;

	//alteração de senha
	@Transient
	@JsonIgnore
	private String senhaNova;
	@Transient
	@JsonIgnore
	private String senhaConfirma;

	@Override
	public String toString() {
		return getNome();
	}

	Boolean isAtivo() {
		return ativo;
	}

	public Boolean validaSenha(String senha) {
		if (senha == null || senha.isEmpty())
			return false;

		return (new BCryptPasswordEncoder()).matches(senha, getSenha());
	}

	public void encodeSenha(String senha) {
		this.senha = (new BCryptPasswordEncoder()).encode(senha);
	}

	public boolean getAtivo() {
		return isAtivo();
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login.toLowerCase();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String string) {
		nome = string;
	}

	@JsonIgnore
	public Set<Permissao> getPermissoes() {
		return grupo.getPermissoes();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaConfirma() {
		return senhaConfirma;
	}

	public void setSenhaConfirma(String senhaConfirma) {
		this.senhaConfirma = senhaConfirma;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}
}
