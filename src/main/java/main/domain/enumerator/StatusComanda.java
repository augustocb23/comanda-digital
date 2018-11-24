package main.domain.enumerator;

public enum StatusComanda {
	A("Aberta"), F("Fechada"), P("Paga"), C("Cancelada");

	private final String desc;

	StatusComanda(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
