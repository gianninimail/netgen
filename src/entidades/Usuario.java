package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import util.EntidadeBase;

@Entity
@Table(name = "user")
public class Usuario implements Serializable, EntidadeBase {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_usuario", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "gen_usuario", sequenceName = "seq_usuario", schema = "ccbh4851system")
	@Column(name = "id")
	Long id;
	
	@Column(name = "name")
	String nome;
	
	@Column(name = "login")
	String login;
	
	@Column(name = "pass")
	String senha;
	
	@Column(name = "tel")
	String tel;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "date_registration")
	@Temporal(TemporalType.DATE)
	Date dataCadastro;
	
	@Column(name = "type_user")
	int tipoUsuario;
	
	public Usuario() {
		this.id = new Long(0);
	}
	
	public Usuario (Long id, String nome, String login, String senha, String tel, String email, Date dtCadastro, int tipoUsuario) {
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.tel = tel;
		this.email = email;
		this.dataCadastro = dtCadastro;
		this.tipoUsuario = tipoUsuario;
		this.dataCadastro = dtCadastro;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Override
	public String toString()
	{
		String saida = this.id + " - " + nome;
		
		return saida;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        
        return true;
	}
}
