package main.persistence.dao;

import main.domain.Produto;

import java.util.List;

public interface ProdutoDao {
	void save(Produto produto);

	void update(Produto produto);

	void delete(Long id);

	Produto findById(Long id);

	List<Produto> findAll();

	List<Produto> findByName(String name);

	boolean exists(String nome, Long id);
}
