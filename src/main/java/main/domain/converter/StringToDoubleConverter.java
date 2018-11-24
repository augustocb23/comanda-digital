package main.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToDoubleConverter implements Converter<String, Double> {

	@Override
	public Double convert(@NonNull String string) {
		if (!string.isEmpty()) {
			return Double.valueOf(string.replaceAll("/./", "").replace(',', '.'));
		} else
			return null;
	}
}
