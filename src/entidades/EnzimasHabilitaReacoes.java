package entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enzime_enable_reaction")
public class EnzimasHabilitaReacoes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public EnzimasHabilitaReacoes() {
		
	}
	
	public EnzimasHabilitaReacoes(Proteina proteina, Enzima enzima) {
		super();
		this.proteina = proteina;
		this.enzima = enzima;
	}
	
	public EnzimasHabilitaReacoes(Proteina proteina, Enzima enzima, Reacao reacao) {
		super();
		this.proteina = proteina;
		this.enzima = enzima;
		this.reacao = reacao;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "reaction_id")
	private Reacao reacao;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "proteina_id")
	Proteina proteina;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "enzima_id")
	private Enzima enzima;

	public Reacao getReacao() {
		return reacao;
	}

	public void setReacao(Reacao reacao) {
		this.reacao = reacao;
	}

	public Proteina getProteina() {
		return proteina;
	}

	public void setProteina(Proteina proteina) {
		this.proteina = proteina;
	}

	public Enzima getEnzima() {
		return enzima;
	}

	public void setEnzima(Enzima enzima) {
		this.enzima = enzima;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enzima == null) ? 0 : enzima.hashCode());
		result = prime * result + ((proteina == null) ? 0 : proteina.hashCode());
		result = prime * result + ((reacao == null) ? 0 : reacao.hashCode());
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
		EnzimasHabilitaReacoes other = (EnzimasHabilitaReacoes) obj;
		if (enzima == null) {
			if (other.enzima != null)
				return false;
		} else if (!enzima.equals(other.enzima))
			return false;
		if (proteina == null) {
			if (other.proteina != null)
				return false;
		} else if (!proteina.equals(other.proteina))
			return false;
		if (reacao == null) {
			if (other.reacao != null)
				return false;
		} else if (!reacao.equals(other.reacao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EnzimaHabilitaReacao [reacao=" + reacao + ", proteina=" + proteina + ", enzima=" + enzima + "]";
	}

}
