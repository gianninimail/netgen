package entidades;

import entidades.Reacao;
import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: GeneAssociation
 *
 */
@Entity
@Table(name = "gene_association")
public class GeneAssociacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private String id;
	
	//@Id
	@ManyToOne
	@JoinColumn(name = "reaction_id")
	private Reacao reacao;
	
	public GeneAssociacao() {
		super();
	}   
	
	public GeneAssociacao(String _id, Reacao _reacao) {
		super();
		this.id = _id;
		this.reacao = _reacao;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}   
	
	public Reacao getReacao() {
		return this.reacao;
	}

	public void setReacao(Reacao reacao) {
		this.reacao = reacao;
	}
   
}