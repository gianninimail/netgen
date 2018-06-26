package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Proteina;

public class ProteinaDAO implements ProteinaINTdao {

	private static ProteinaDAO instancia;
	protected EntityManager entityManager;
	
	public ProteinaDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static ProteinaDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new ProteinaDAO();
		}
		
		return instancia;
	}

	@Override
	public void Inserir(Proteina _proteina) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_proteina);
		
		this.entityManager.getTransaction().commit();
		
	}

	@Override
	public void Editar(Proteina _proteina) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_proteina);
		
		this.entityManager.getTransaction().commit();
		
	}

	@Override
	public void Excluir(Proteina _proteina) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		Proteina proteina = entityManager.find(Proteina.class, _proteina.getId());
		
		this.entityManager.remove(proteina);
		
		this.entityManager.getTransaction().commit();
		
	}

	@Override
	public void ExcluirPorID(String _id) throws SQLException {

		Proteina proteina = PegarPeloID(_id);
		
		Excluir(proteina);
		
	}

	@Override
	public Proteina PegarPeloID(String _id) throws SQLException {

		Proteina proteina = entityManager.find(Proteina.class, _id);
		
		return proteina;
	}

	@Override
	public List<Proteina> listarTodos() throws SQLException {

		Query q = entityManager.createQuery("from Proteina r", Proteina.class);
		
		@SuppressWarnings("unchecked")
		List<Proteina> proteinas = q.getResultList();
		
		return proteinas;
	}

	@Override
	public boolean ExisteProteina(String _id) throws SQLException {

		Proteina proteina = entityManager.find(Proteina.class, _id);
		
		if (proteina != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
