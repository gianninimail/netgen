package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "enzima_brenda")
public class EnzimaBrenda implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	String ec_number;
	@Column(length = 11000)
	String sequencia;
	
	public EnzimaBrenda() {
		
	}
	
	public EnzimaBrenda(String _ec_number, String _sequencia) {
		super();
		this.ec_number = _ec_number;
		this.sequencia = _sequencia;
	}

	public String getEc_number() {
		return ec_number;
	}
	
	public void setEc_number(String ec_number) {
		this.ec_number = ec_number;
	}
	
	public String getSequencia() {
		return sequencia;
	}
	
	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}
}
