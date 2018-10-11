package main.domain;

import main.domain.enumerator.Unidade;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "produto")
public class Produto extends AbstractEntity<Long> {
	@Column(name = "nome", nullable = false, unique = true)
	private String nome;
	@Column(name = "estoque")
	private Integer estoque;
	@Transient
	private boolean estoqueNull;
	@ManyToMany
	@JoinTable(name = "produto_ingrediente",
			joinColumns = {@JoinColumn(name = "produto")},
			inverseJoinColumns = {@JoinColumn(name = "ingrediente")})
	private List<Ingrediente> ingredientes;
	@Column
	private String imagem;
	@Column(columnDefinition = "CHAR(2)")
	@Enumerated(EnumType.STRING)
	private Unidade unidade;
	@Column(columnDefinition = "DECIMAL(6,2)")
	@NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "###0.00")
	private Double preco;

	@Override
	public String toString() {
		return nome;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public boolean isEstoqueNull() {
		return estoque == null;
	}

	public void setEstoqueNull(boolean estoqueNull) {
		this.estoqueNull = estoqueNull;
	}
}
