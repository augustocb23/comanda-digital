package main.persistence.dao;

import main.domain.Funcionario;

import java.util.List;

public interface FuncionarioDao {
	void save(Funcionario funcionario);

	void update(Funcionario funcionario);

	void delete(Long id);

	Funcionario findById(Long id);

	List<Funcionario> findAll();

	Funcionario findByLogin(String login);

	boolean exists(String login, Long id);
}
