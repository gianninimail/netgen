package entidades;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gene")
public class Gene implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "seq_nuc")
	private String seq_nuc;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "reference_id")
	private String reference_id;
	
	@Column(name = "has_grp")
	private boolean tem_grp;
	
	@ManyToMany
	@JoinTable(name = "genomas_genes", joinColumns = @JoinColumn(name = "gene_id"), inverseJoinColumns = @JoinColumn(name = "genoma_id"))
	private Set<Genoma> genomas;
	
	public Gene() {
		
	}
	
	public Gene(String _id, String _seq_nuc, String _reference_id, Set<Genoma> _genomas) {
		super();
		this.id = _id;
		this.seq_nuc = _seq_nuc;
		this.reference_id = _reference_id;
		this.genomas = _genomas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSeq_nuc() {
		return seq_nuc;
	}

	public void setSeq_nuc(String seq_nuc) {
		this.seq_nuc = seq_nuc;
	}

	public String getReference_id() {
		return reference_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}

	public boolean isTem_grp() {
		return tem_grp;
	}

	public void setTem_grp(boolean tem_grp) {
		this.tem_grp = tem_grp;
	}

	public void setGenomas(Set<Genoma> genomas) {
		this.genomas = genomas;
	}

	public Set<Genoma> getGenomas() {
		return genomas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Gene other = (Gene) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
