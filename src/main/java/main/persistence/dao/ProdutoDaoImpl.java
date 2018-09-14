package main.persistence.dao;

import main.domain.Produto;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoDaoImpl extends AbstractDao<Produto, Long> implements ProdutoDao {
}
