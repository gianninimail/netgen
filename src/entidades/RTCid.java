package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RTCid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "reaction_id")
	private String reacao_id;
	
	@Column(name = "compound_id")
	private String componente_id;
	
	@Column(name = "substrate_or_product")
	private char tipo_R_ou_P;

	public RTCid() {
		
	}

	public RTCid(String reacao_id, String componente_id, char tipo_R_ou_P) {
		super();
		this.reacao_id = reacao_id;
		this.componente_id = componente_id;
		this.tipo_R_ou_P = tipo_R_ou_P;
	}

	public String getReacao_id() {
		return reacao_id;
	}

	public void setReacao_id(String reacao_id) {
		this.reacao_id = reacao_id;
	}

	public String getComponente_id() {
		return componente_id;
	}

	public void setComponente_id(String componente_id) {
		this.componente_id = componente_id;
	}

	public char getTipo_R_ou_P() {
		return tipo_R_ou_P;
	}

	public void setTipo_R_ou_P(char tipo_R_ou_P) {
		this.tipo_R_ou_P = tipo_R_ou_P;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componente_id == null) ? 0 : componente_id.hashCode());
		result = prime * result + ((reacao_id == null) ? 0 : reacao_id.hashCode());
		result = prime * result + tipo_R_ou_P;
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
		RTCid other = (RTCid) obj;
		if (componente_id == null) {
			if (other.componente_id != null)
				return false;
		} else if (!componente_id.equals(other.componente_id))
			return false;
		if (reacao_id == null) {
			if (other.reacao_id != null)
				return false;
		} else if (!reacao_id.equals(other.reacao_id))
			return false;
		if (tipo_R_ou_P != other.tipo_R_ou_P)
			return false;
		return true;
	}
}
