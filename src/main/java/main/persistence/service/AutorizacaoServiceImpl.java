package main.persistence.service;

import main.domain.Permissao;
import main.persistence.dao.AutorizacaoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AutorizacaoServiceImpl implements AutorizacaoService {
	private final AutorizacaoDao dao;

	@Autowired
	public AutorizacaoServiceImpl(AutorizacaoDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public void salvar(Permissao permissao) {
		dao.save(permissao);
	}

	@Override
	@Transactional
	public void editar(Permissao permissao) {
		dao.update(permissao);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Permissao buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Permissao> buscarTodos() {
		return dao.findAll();
	}
}
