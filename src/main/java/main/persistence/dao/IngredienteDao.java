package main.persistence.dao;

import main.domain.Ingrediente;

import java.util.List;

public interface IngredienteDao {
	void save(Ingrediente ingrediente);

	void update(Ingrediente ingrediente);

	void delete(Long id);

	Ingrediente findById(Long id);

	List<Ingrediente> findAll();
}
