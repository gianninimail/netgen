package entidades;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Reconstrucao
 *
 */
@Entity
@Table(name="reconstruction")

public class Reconstrucao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_reconstrucao", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "gen_reconstrucao", sequenceName = "seq_reconstrucao", schema = "ccbh4851system")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title")
	private String titulo;
	
	@Column(name = "log", length = 5000)
	private String log;
	
	@Column(name = "sbml")
	private String sbml;
	
	@Column(name = "version")
	private String version;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date data;
	
	
	@ManyToOne
	@JoinColumn(name = "genoma_id")
	private Genoma genoma;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Usuario user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reconstrucao")
	private Collection<ReconstrucaoTemReacoes> rhrs;
	
	public Reconstrucao() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getLog() {
		return this.log;
	}

	public void setLog(String log) {
		this.log = log;
	}   
	public String getSbml() {
		return this.sbml;
	}

	public void setSbml(String sbml) {
		this.sbml = sbml;
	}   
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
		
	/*
	public Collection<Reacao> getReacoes() {
		return reacoes;
	}
	
	public void setReacoes(Collection<Reacao> reacoes) {
		this.reacoes = reacoes;
	}
	*/
	
	public Genoma getGenoma() {
		return genoma;
	}
	public void setGenoma(Genoma genoma) {
		this.genoma = genoma;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public Collection<ReconstrucaoTemReacoes> getRhrs() {
		return rhrs;
	}
	public void setRhrs(Collection<ReconstrucaoTemReacoes> rhrs) {
		this.rhrs = rhrs;
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
		Reconstrucao other = (Reconstrucao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Reconstrucao [id=" + id + ", titulo=" + titulo + ", version=" + version + "]";
	}
}
