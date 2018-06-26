package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Enzima;

public class EnzimeDAO implements EnzimeINTdao {

	private static EnzimeDAO instancia;
	protected EntityManager entityManager;
	
	public EnzimeDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static EnzimeDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new EnzimeDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(Enzima _enzime) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_enzime);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Editar(Enzima _enzime) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_enzime);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(Enzima _enzime) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		Enzima enzime = entityManager.find(Enzima.class, _enzime.getEc_number());
		
		this.entityManager.remove(enzime);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void ExcluirPorID(String _ec) throws SQLException {

		Enzima enzime = PegarPeloID(_ec);
		
		Excluir(enzime);

	}

	@Override
	public Enzima PegarPeloID(String _ec) throws SQLException {

		Enzima enzime = entityManager.find(Enzima.class, _ec);
		
		return enzime;
	}

	@Override
	public List<Enzima> listarTodos() throws SQLException {
		
		Query q = entityManager.createQuery("from Enzime r", Enzima.class);
		
		@SuppressWarnings("unchecked")
		List<Enzima> enzimas = q.getResultList();
		
		return enzimas;
	}

	@Override
	public boolean ExisteEnzime(String _ec) throws SQLException {

		Enzima enzime = entityManager.find(Enzima.class, _ec);
		
		if (enzime != null) {
			return true;
		}
		else {
			return false;
		}
	}

}
