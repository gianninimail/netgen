package entidades;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "compound")
public class Componente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "abbreviation")
	String abbreviation;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "formula")
	String formula;
	
	@Column(name = "charge")
	int charge;
	
	@Column(name = "charge_formula")
	String charge_formula;
	
	@Column(name = "id_kegg")
	String id_kegg;
	
	//@Column(name = "structure")
	//Blob structure;
	
	@OneToMany(mappedBy = "componente")
	private Collection<ReacaoTemComponentes> rhcs;
	
	public Componente() {
		
	}
	
	public Componente(String _abbreviation, String _name, String _formula, int _charge, String _charge_formula, String _id_kegg) {
		super();
		//this.id = _id;
		this.abbreviation = _abbreviation;
		this.name = _name;
		this.formula = _formula;
		this.charge = _charge;
		this.charge_formula = _charge_formula;
		this.id_kegg = _id_kegg;
		//this.structure = _structure;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public String getFormula() {
		return formula;
	}
	
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public String getCharge_formula() {
		return charge_formula;
	}
	
	public void setCharge_formula(String charge_formula) {
		this.charge_formula = charge_formula;
	}

	public String getId_kegg() {
		return id_kegg;
	}

	public void setId_kegg(String id_kegg) {
		this.id_kegg = id_kegg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
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
		Componente other = (Componente) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null)
				return false;
		} else if (!abbreviation.equals(other.abbreviation))
			return false;
		return true;
	}
	
	/*
	public Blob getStructure() {
		return structure;
	}
	
	public void setStructure(Blob structure) {
		this.structure = structure;
	}
	*/
}
