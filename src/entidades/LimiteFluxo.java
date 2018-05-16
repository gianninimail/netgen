package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flux_bound")
public class LimiteFluxo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "constant")
	private Boolean contante;
	
	@Column(name = "value")
	private Double valor;
	
	public LimiteFluxo() {
		
	}

	public LimiteFluxo(String id, Boolean contante, Double valor) {
		super();
		this.id = id;
		this.contante = contante;
		this.valor = valor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getContante() {
		return contante;
	}

	public void setContante(Boolean contante) {
		this.contante = contante;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contante == null) ? 0 : contante.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LimiteFluxo other = (LimiteFluxo) obj;
		if (contante == null) {
			if (other.contante != null)
				return false;
		} else if (!contante.equals(other.contante))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LimiteFluxo [id=" + id + ", contante=" + contante + ", valor=" + valor + "]";
	}
}
