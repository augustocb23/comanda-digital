package main.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractEntity<Codigo extends Serializable> implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Codigo codigo;

	@Override
	public int hashCode() {
		return Objects.hashCode(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity<?> other = (AbstractEntity<?>) obj;
		if (codigo == null) {
			return other.codigo == null;
		} else
			return codigo.equals(other.codigo);
	}

	@Override
	public String toString() {
		return String.valueOf(codigo);
	}
}
