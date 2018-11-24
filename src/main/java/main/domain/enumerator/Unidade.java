package main.domain.enumerator;

public enum Unidade {
	un("unidades"), kg("quilos"), lt("litros");

	private final String desc;

	Unidade(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
