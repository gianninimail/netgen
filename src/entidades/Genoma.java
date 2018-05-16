package entidades;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "genoma")
public class Genoma implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String nome;
	
	@Column(name = "version")
	private float versao;
	
	@Column(name = "ncbi_id")
	private String ncbi_id;
	
	@OneToOne
	@JoinColumn(name = "organism_id")
	private Organismo organismo;
	
	@ManyToMany(mappedBy = "genomas")
	private Set<Gene> genes;
	
	public Genoma() {
		
	}
	
	public Genoma(Long _id, String _nome, float _versao, String _ncbi_id, Organismo _organismo) {
		super();
		this.id = _id;
		this.nome = _nome;
		this.versao = _versao;
		this.ncbi_id = _ncbi_id;
		this.organismo = _organismo;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public float getVersao() {
		return versao;
	}
	
	public void setVersao(float versao) {
		this.versao = versao;
	}
	
	public String getNcbi_id() {
		return ncbi_id;
	}
	
	public void setNcbi_id(String ncbi_id) {
		this.ncbi_id = ncbi_id;
	}
	
	
		
}
