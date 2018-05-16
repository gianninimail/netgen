package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Usuario;

public class UsuarioDAO implements UsuarioINTdao {

	private static UsuarioDAO instancia;
	protected EntityManager entityManager;
	
	public UsuarioDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static UsuarioDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new UsuarioDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(Usuario _usuario) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_usuario);
		
		this.entityManager.getTransaction().commit();
	}

	@Override
	public void Editar(Usuario _usuario) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_usuario);
		
		this.entityManager.getTransaction().commit();
	}

	@Override
	public void Excluir(Usuario _usuario) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		Usuario usuario = entityManager.find(Usuario.class, _usuario.getId());
		
		this.entityManager.remove(usuario);
		
		this.entityManager.getTransaction().commit();
		
	}

	@Override
	public void ExcluirPorID(Long _id) throws SQLException {

		Usuario usuario = PegarPeloID(_id);
		
		Excluir(usuario);
		
	}

	@Override
	public Usuario PegarPeloID(Long _id) throws SQLException {

		Usuario usuario = entityManager.find(Usuario.class, _id);
		
		return usuario;
	}

	@Override
	public List<Usuario> listarTodos() throws SQLException {
		
		Query q = entityManager.createQuery("from Compound r", Usuario.class);
		
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = q.getResultList();
		
		return usuarios;
	}

	@Override
	public Usuario ExisteUsuario(String _login, String _senha) throws SQLException {

		//Usuario usuario = entityManager.find(Usuario.class, abbreviation);
		Query query = entityManager.createQuery("SELECT o FROM Usuario o WHERE o.login = :login and o.senha = :senha");
		query.setParameter("login", _login);
		query.setParameter("senha", _senha);
		
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = query.getResultList();
		
		if (!usuarios.isEmpty()) {
			return usuarios.get(0);
		}
		else {
			return null;
		}
	}

}
