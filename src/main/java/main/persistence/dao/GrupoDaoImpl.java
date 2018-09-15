package main.persistence.dao;

import main.domain.Grupo;
import org.springframework.stereotype.Repository;

@Repository
public class GrupoDaoImpl extends AbstractDao<Grupo, Long> implements GrupoDao {
}
