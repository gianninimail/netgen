package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.Genoma;

public class GenomaDAO implements GenomaINTdao {

	private static GenomaDAO instancia;
	protected EntityManager entityManager;
	
	public GenomaDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static GenomaDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new GenomaDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(Genoma _genoma) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_genoma);
		
		this.entityManager.getTransaction().commit();

	}
	
	@Override
	public void Editar(Genoma _genoma) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_genoma);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(Genoma _genoma) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		Genoma genoma = entityManager.find(Genoma.class, _genoma.getId());
		
		this.entityManager.remove(genoma);
		
		this.entityManager.getTransaction().commit();

	}
	
	@Override
	public void ExcluirPorID(String _id) throws SQLException {
		
		Genoma genoma = PegarPeloID(_id);
		
		Excluir(genoma);
		
	}

	@Override
	public Genoma PegarPeloID(String _id) throws SQLException {
		
		Genoma genoma = entityManager.find(Genoma.class, _id);
		
		return genoma;
	}

	@Override
	public Long PegarID() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Genoma> listarTodos() throws SQLException {
		
		@SuppressWarnings("unchecked")
		List<Genoma> genomas = entityManager.createQuery("FROM " + Genoma.class.getName()).getResultList();
		
		return genomas;
	}
	
}
