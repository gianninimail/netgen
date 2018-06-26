package entidades;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "reaction")
public class Reacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "abbreviation")
	private String abbreviation;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "definition", length = 1500)
	private String definition;
	
	@Column(name = "equation", length = 1500)
	private String equation;
	
	@Column(name = "id_kegg")
	private String id_kegg;
	
	//@Column(name = "link")
	//String link;
	
	//@Column(name = "structure")
	//Blob structure;
	
	@Column(name = "reverse")
	private boolean reverse;
	
	@Column(name = "genes_association", length = 1500)
	private String associacao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reacao")
	private Collection<ReacaoTemComponentes> rhcs;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reacao")
	private Collection<ReconstrucaoTemReacoes> rhrs;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reacao")
	private Collection<EnzimasHabilitaReacoes> ehrs;
	
	public Reacao() {
		
	}
	
	public Reacao(String _abbreviation, String _name, String _definition, String _equation, String _id_kegg, boolean _reverse) {
		super();
		//this.id = _id;
		this.abbreviation = _abbreviation;
		this.name = _name;
		this.definition = _definition;
		this.equation = _equation;
		this.id_kegg = _id_kegg;
		this.reverse = _reverse;
		//this.link = _link;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getEquation() {
		return equation;
	}

	public void setEquation(String equation) {
		this.equation = equation;
	}

	/*
	public Blob getStructure() {
		return structure;
	}

	public void setStructure(Blob structure) {
		this.structure = structure;
	}
	*/

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public String getId_kegg() {
		return id_kegg;
	}

	public void setId_kegg(String id_kegg) {
		this.id_kegg = id_kegg;
	}

	public String getAssociacao() {
		return associacao;
	}

	public void setAssociacao(String associacao) {
		this.associacao = associacao;
	}

	public Collection<ReacaoTemComponentes> getRhcs() {
		return rhcs;
	}

	public void setRhcs(Collection<ReacaoTemComponentes> rhcs) {
		this.rhcs = rhcs;
	}

	public Collection<ReconstrucaoTemReacoes> getRhrs() {
		return rhrs;
	}

	public void setRhrs(Collection<ReconstrucaoTemReacoes> rhrs) {
		this.rhrs = rhrs;
	}

	public Collection<EnzimasHabilitaReacoes> getEhrs() {
		return ehrs;
	}

	public void setEhrs(Collection<EnzimasHabilitaReacoes> ehrs) {
		this.ehrs = ehrs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "Reacao [abbreviation=" + abbreviation + ", name=" + name + ", definition=" + definition + ", equation="
				+ equation + ", associacao=" + associacao + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reacao other = (Reacao) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null)
				return false;
		} else if (!abbreviation.equals(other.abbreviation))
			return false;
		return true;
	}
}
