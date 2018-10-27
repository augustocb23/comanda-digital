package main.persistence.service;

import main.domain.Permissao;
import main.persistence.dao.PermissaoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PermissaoServiceImpl implements PermissaoService {
	private final PermissaoDao dao;

	@Autowired
	public PermissaoServiceImpl(PermissaoDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Permissao> buscarTodos() {
		List<Permissao> all = dao.findAll();
		all.sort(Comparator.comparing(Permissao::getNome));
		return all;
	}

	@Override
	public Permissao buscarPorId(Long codigo) {
		return dao.findById(codigo);
	}
}
