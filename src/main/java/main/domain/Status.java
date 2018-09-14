package main.domain;

public enum Status {
	S("Solicitado"),
	P("Preparando"),
	R("Pronto"),
	E("Entregue"), C("Cancelado");

	private String desc;

	Status(String desc) {
		this.desc = desc;
	}
}
