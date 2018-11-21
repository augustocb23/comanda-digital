package main.persistence.service;

import main.domain.Funcionario;
import main.persistence.dao.FuncionarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FuncionarioServiceImpl implements FuncionarioService {
	private final FuncionarioDao dao;

	@Autowired
	public FuncionarioServiceImpl(FuncionarioDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public void salvar(Funcionario funcionario) {
		dao.save(funcionario);
	}

	@Override
	@Transactional
	public void editar(Funcionario funcionario) {
		dao.update(funcionario);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Funcionario buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcionario> buscarTodos() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Funcionario buscarPorLogin(String login) {
		return dao.findByLogin(login);
	}

	@Override
	public boolean existe(String login, Long codigo) {
		return dao.exists(login, codigo);
	}
}
