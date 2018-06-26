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
@Table(name = "enzime")
public class Enzima implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ec_number")
	private String ec_number;
	
	@Column(name = "class")
	private String classs;
	
	@Column(name = "sysname")
	private String sysname;
	
	@Column(name = "reaction_iubmb", length = 1000)
	private String reaction_iubmb;
	
	@Column(name = "reaction_kegg")
	private String reaction_kegg;
	
	@Column(name = "substract")
	private String substract;
	
	@Column(name = "product")
	private String product;
	
	@Column(name = "comment", length = 2000)
	private String comment;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "enzima")
	private Collection<EnzimasHabilitaReacoes> ehrs;

	public String getEc_number() {
		return ec_number;
	}

	public void setEc_number(String ec_number) {
		this.ec_number = ec_number;
	}

	public String getClasss() {
		return classs;
	}

	public void setClasss(String classs) {
		this.classs = classs;
	}

	public String getSysname() {
		return sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	public String getReaction_iubmb() {
		return reaction_iubmb;
	}

	public void setReaction_iubmb(String reaction_iubmb) {
		this.reaction_iubmb = reaction_iubmb;
	}

	public String getReaction_kegg() {
		return reaction_kegg;
	}

	public void setReaction_kegg(String reaction_kegg) {
		this.reaction_kegg = reaction_kegg;
	}

	public String getSubstract() {
		return substract;
	}

	public void setSubstract(String substract) {
		this.substract = substract;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
		result = prime * result + ((ec_number == null) ? 0 : ec_number.hashCode());
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
		Enzima other = (Enzima) obj;
		if (ec_number == null) {
			if (other.ec_number != null)
				return false;
		} else if (!ec_number.equals(other.ec_number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Enzime [ec_number=" + ec_number + ", classs=" + classs + ", sysname=" + sysname + ", reaction_iubmb="
				+ reaction_iubmb + ", reaction_kegg=" + reaction_kegg + "]";
	}
	
}
