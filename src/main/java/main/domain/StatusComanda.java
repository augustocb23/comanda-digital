package main.domain;

public enum StatusComanda {
	A("Aberta"), F("Fechada"), P("Paga"), C("Cancelada");

	private String desc;

	StatusComanda(String desc) {
		this.desc = desc;
	}
}
