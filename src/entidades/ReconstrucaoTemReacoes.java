package entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reconstruction_has_reaction")
public class ReconstrucaoTemReacoes {

	@Id
	@ManyToOne
	@JoinColumn(name = "reconstruction_id")
	private Reconstrucao reconstrucao;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "reaction_id")
	private Reacao reacao;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "lower_flux_id")
	private LimiteFluxo limiteInferior;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "upper_flux_id")
	private LimiteFluxo limiteSuperior;
	
	public ReconstrucaoTemReacoes() {
		
	}

	public Reconstrucao getReconstrucao() {
		return reconstrucao;
	}

	public void setReconstrucao(Reconstrucao reconstrucao) {
		this.reconstrucao = reconstrucao;
	}

	public Reacao getReacao() {
		return reacao;
	}

	public void setReacao(Reacao reacao) {
		this.reacao = reacao;
	}

	public LimiteFluxo getLimiteInferior() {
		return limiteInferior;
	}

	public void setLimiteInferior(LimiteFluxo limiteInferior) {
		this.limiteInferior = limiteInferior;
	}

	public LimiteFluxo getLimiteSuperior() {
		return limiteSuperior;
	}

	public void setLimiteSuperior(LimiteFluxo limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}
}
