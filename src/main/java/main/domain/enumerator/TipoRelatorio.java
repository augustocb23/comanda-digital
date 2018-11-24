package main.domain.enumerator;

public enum TipoRelatorio {
	C("Consumo"), F("Financeiro");

	private final String desc;

	TipoRelatorio(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
