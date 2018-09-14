package main.persistence.service;

import main.domain.Ingrediente;
import main.persistence.dao.IngredienteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngredienteServiceImpl implements IngredienteService {
	private final IngredienteDao dao;

	@Autowired
	public IngredienteServiceImpl(IngredienteDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public void salvar(Ingrediente ingrediente) {
		dao.save(ingrediente);
	}

	@Override
	@Transactional
	public void editar(Ingrediente ingrediente) {
		dao.update(ingrediente);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Ingrediente buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ingrediente> buscarTodos() {
		return dao.findAll();
	}
}
