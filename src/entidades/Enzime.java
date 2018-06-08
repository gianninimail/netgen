package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enzime")
public class Enzime implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ec_number")
	private String ec_number;
	
	@Column(name = "class")
	private String classs;
	
	@Column(name = "sysname")
	private String sysname;
	
	@Column(name = "reaction_iubmb")
	private String reaction_iubmb;
	
	@Column(name = "reaction_kegg")
	private String reaction_kegg;
	
	@OneToOne
	@JoinColumn(name = "proteina_id")
	private Proteina proteina;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ec_number == null) ? 0 : ec_number.hashCode());
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
		Enzime other = (Enzime) obj;
		if (ec_number == null) {
			if (other.ec_number != null)
				return false;
		} else if (!ec_number.equals(other.ec_number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Enzime [ec_number=" + ec_number + ", classs=" + classs + ", sysname=" + sysname + ", reaction_iubmb="
				+ reaction_iubmb + ", reaction_kegg=" + reaction_kegg + "]";
	}
	
}
