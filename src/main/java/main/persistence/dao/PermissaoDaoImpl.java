package main.persistence.dao;

import main.domain.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public class PermissaoDaoImpl extends AbstractDao<Permissao, Long> implements PermissaoDao {
}
