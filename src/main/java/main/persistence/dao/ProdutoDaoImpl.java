package main.persistence.dao;

import main.domain.Produto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoDaoImpl extends AbstractDao<Produto, Long> implements ProdutoDao {
	@Override
	public List<Produto> findByName(String name) {
		return createQuery("FROM Produto WHERE nome LIKE ?1", "%" + name + "%");
	}

	@Override
	public boolean exists(String name, Long id) {
		return existsItemWithColumn("nome", name, id);
	}
}
