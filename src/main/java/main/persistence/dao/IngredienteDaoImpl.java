package main.persistence.dao;

import main.domain.Ingrediente;
import org.springframework.stereotype.Repository;

@Repository
public class IngredienteDaoImpl extends AbstractDao<Ingrediente, Long> implements IngredienteDao {
}
