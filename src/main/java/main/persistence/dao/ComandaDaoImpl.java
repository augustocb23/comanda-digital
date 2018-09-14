package main.persistence.dao;

import main.domain.Comanda;
import org.springframework.stereotype.Repository;

@Repository
public class ComandaDaoImpl extends AbstractDao<Comanda, Long> implements ComandaDao {
}
