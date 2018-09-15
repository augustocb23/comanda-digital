package main.domain;

public enum StatusPedido {
	S("Solicitado"), P("Preparando"), R("Pronto"), E("Entregue"), C("Cancelado");

	private String desc;

	StatusPedido(String desc) {
		this.desc = desc;
	}
}
