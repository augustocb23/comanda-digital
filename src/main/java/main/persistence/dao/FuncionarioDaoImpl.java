package main.persistence.dao;

import main.domain.Funcionario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FuncionarioDaoImpl extends AbstractDao<Funcionario, Long> implements FuncionarioDao {
	@Override
	public Funcionario findByLogin(String login) {
		List<Funcionario> result = createQuery("SELECT f FROM Funcionario f WHERE login = ?1", login);
		if (result.size() > 0)
			return result.get(0);
		else
			return null;
	}
}
