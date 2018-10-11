package main.domain.converter;

import main.domain.Grupo;
import main.persistence.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGrupoConverter implements Converter<String, Grupo> {
	private final GrupoService service;

	@Autowired
	public StringToGrupoConverter(GrupoService service) {
		this.service = service;
	}

	@Override
	public Grupo convert(String string) {
		if (!string.isEmpty()) {
			Long codigo = Long.valueOf(string);
			return service.buscarPorId(codigo);
		} else
			return null;
	}
}
