package main.persistence.service;

import main.domain.Grupo;
import main.persistence.dao.GrupoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GrupoServiceImpl implements GrupoService {
	private final GrupoDao dao;

	@Autowired
	public GrupoServiceImpl(GrupoDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public void salvar(Grupo grupo) {
		dao.save(grupo);
	}

	@Override
	@Transactional
	public void editar(Grupo grupo) {
		dao.update(grupo);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Grupo buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Grupo> buscarTodos() {
		return dao.findAll();
	}
}
