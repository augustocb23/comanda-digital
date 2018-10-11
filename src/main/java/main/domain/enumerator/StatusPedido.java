package main.domain.enumerator;

public enum StatusPedido {
	S("Solicitado"), P("Preparando"), E("Entregue"), C("Cancelado");

	private String desc;

	StatusPedido(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
