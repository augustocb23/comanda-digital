package main.persistence.service;

import main.domain.Produto;
import main.persistence.dao.ProdutoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {
	private final ProdutoDao dao;

	@Autowired
	public ProdutoServiceImpl(ProdutoDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public void salvar(Produto produto) {
		dao.save(produto);
	}

	@Override
	@Transactional
	public void editar(Produto produto) {
		dao.update(produto);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Produto buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Produto> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Produto> buscarPorNome(String nome) {
		return dao.findByName(nome);
	}
}
