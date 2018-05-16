package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Componente;

public class ComponenteDAO implements ComponenteINTdao {

	private static ComponenteDAO instancia;
	protected EntityManager entityManager;
	
	public ComponenteDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static ComponenteDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new ComponenteDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(Componente _compound) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_compound);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Editar(Componente _compound) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_compound);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(Componente _compound) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		Componente compound = entityManager.find(Componente.class, _compound.getAbbreviation());
		
		this.entityManager.remove(compound);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void ExcluirPorID(String _abbreviation) throws SQLException {
		
		Componente compound = PegarPeloID(_abbreviation);
		
		Excluir(compound);

	}

	@Override
	public Componente PegarPeloID(String abbreviation) throws SQLException {
		
		Componente compound = entityManager.find(Componente.class, abbreviation);
		
		return compound;
	}

	@Override
	public List<Componente> listarTodos() throws SQLException {
		
		Query q = entityManager.createQuery("from Compound r", Componente.class);
		
		@SuppressWarnings("unchecked")
		List<Componente> compounds = q.getResultList();
		
		return compounds;
	}

	@Override
	public boolean ExisteCompound(String abbreviation) throws SQLException {
		
		Componente compound = entityManager.find(Componente.class, abbreviation);
		
		if (compound != null) {
			return true;
		}
		else {
			return false;
		}
	}

}
