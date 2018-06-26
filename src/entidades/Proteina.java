package entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Proteina
 *
 */
@Entity
@Table(name = "protein")
public class Proteina implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "name")
	private String nome;
	
	@Column(name = "seq_aa", length = 5000)
	private String seq_aa;
	
	@OneToOne
	@JoinColumn(name = "gene_id")
	Gene gene;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proteina")
	private Collection<EnzimasHabilitaReacoes> ehrs;

	public Proteina() {
		super();
	}   
	
	public Proteina(String _id, String _nome, String _seq_aa, Gene _gene) {
		super();
		this.id = _id;
		this.nome = _nome;
		this.seq_aa = _seq_aa;
		this.gene = _gene;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}   
	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}   
	
	public String getSeq_aa() {
		return this.seq_aa;
	}

	public void setSeq_aa(String seq_aa) {
		this.seq_aa = seq_aa;
	}
	
	public Gene getGene() {
		return gene;
	}

	public void setGene(Gene gene) {
		this.gene = gene;
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
		Proteina other = (Proteina) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
