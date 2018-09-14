package main.persistence.service;

import main.domain.Conversao;
import main.persistence.dao.ConversaoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConversaoServiceImpl implements ConversaoService {
	private final ConversaoDao dao;

	@Autowired
	public ConversaoServiceImpl(ConversaoDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public void salvar(Conversao conversao) {
		dao.save(conversao);
	}

	@Override
	@Transactional
	public void editar(Conversao conversao) {
		dao.update(conversao);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Conversao buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Conversao> buscarTodos() {
		return dao.findAll();
	}
}
