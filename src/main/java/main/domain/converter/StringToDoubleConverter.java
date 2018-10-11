package main.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDoubleConverter implements Converter<String, Double> {

	@Override
	public Double convert(String string) {
		if (!string.isEmpty()) {
			return Double.valueOf(string.replaceAll("/./", "").replace(',', '.'));
		} else
			return null;
	}
}
