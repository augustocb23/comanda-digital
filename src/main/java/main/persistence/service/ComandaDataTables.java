package main.persistence.service;

import main.domain.Comanda;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface ComandaDataTables extends DataTablesRepository<Comanda, Long> {
}
