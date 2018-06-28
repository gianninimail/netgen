package entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Ortholog database table.
 * 
 */
//@Entity
//@NamedQuery(name="Ortholog.findAll", query="SELECT o FROM Ortholog o")
//@Table(name = "Ortholog")
public class Ortholog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="NORMALIZED_SCORE")
	private float normalizedScore;

	@Id
	@Column(name="SEQUENCE_ID_A")
	private String sequenceIdA;

	@Column(name="SEQUENCE_ID_B")
	private String sequenceIdB;

	@Column(name="TAXON_ID_A")
	private String taxonIdA;

	@Column(name="TAXON_ID_B")
	private String taxonIdB;

	@Column(name="UNNORMALIZED_SCORE")
	private float unnormalizedScore;

	public Ortholog() {
	}

	public float getNormalizedScore() {
		return this.normalizedScore;
	}

	public void setNormalizedScore(float normalizedScore) {
		this.normalizedScore = normalizedScore;
	}

	public String getSequenceIdA() {
		return this.sequenceIdA;
	}

	public void setSequenceIdA(String sequenceIdA) {
		this.sequenceIdA = sequenceIdA;
	}

	public String getSequenceIdB() {
		return this.sequenceIdB;
	}

	public void setSequenceIdB(String sequenceIdB) {
		this.sequenceIdB = sequenceIdB;
	}

	public String getTaxonIdA() {
		return this.taxonIdA;
	}

	public void setTaxonIdA(String taxonIdA) {
		this.taxonIdA = taxonIdA;
	}

	public String getTaxonIdB() {
		return this.taxonIdB;
	}

	public void setTaxonIdB(String taxonIdB) {
		this.taxonIdB = taxonIdB;
	}

	public float getUnnormalizedScore() {
		return this.unnormalizedScore;
	}

	public void setUnnormalizedScore(float unnormalizedScore) {
		this.unnormalizedScore = unnormalizedScore;
	}

}