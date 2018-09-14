package main.persistence.dao;

import main.domain.Conversao;
import org.springframework.stereotype.Repository;

@Repository
public class ConversaoDaoImpl extends AbstractDao<Conversao, Long> implements ConversaoDao {
}
