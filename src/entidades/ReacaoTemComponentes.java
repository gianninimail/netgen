package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reaction_has_compound")
public class ReacaoTemComponentes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "stoichiometry")
	private double estequiometria;
	
	@Column(name = "compartment")
	private char compartimento;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "reaction_id")
	private Reacao reacao;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "compound_id")
	private Componente componente;
	
	@Id
	@Column(name = "substrate_or_product")
	char tipo_P_ou_R;
	
	public ReacaoTemComponentes() {
		
	}

	public double getEstequiometria() {
		return estequiometria;
	}

	public void setEstequiometria(double estequiometria) {
		this.estequiometria = estequiometria;
	}

	public char getCompartimento() {
		return compartimento;
	}

	public void setCompartimento(char compartimento) {
		this.compartimento = compartimento;
	}

	public Reacao getReacao() {
		return reacao;
	}

	public void setReacao(Reacao reacao) {
		this.reacao = reacao;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public char getTipo_P_ou_R() {
		return tipo_P_ou_R;
	}

	public void setTipo_P_ou_R(char tipo_P_ou_R) {
		this.tipo_P_ou_R = tipo_P_ou_R;
	}
}
