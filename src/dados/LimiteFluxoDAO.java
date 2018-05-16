package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.LimiteFluxo;

public class LimiteFluxoDAO implements LimiteFluxoINTdao {

	private static LimiteFluxoDAO instancia;
	protected EntityManager entityManager;
	
	public LimiteFluxoDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static LimiteFluxoDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new LimiteFluxoDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(LimiteFluxo _limite) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_limite);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Editar(LimiteFluxo _limite) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_limite);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(LimiteFluxo _limite) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		LimiteFluxo limite = entityManager.find(LimiteFluxo.class, _limite.getId());
		
		this.entityManager.remove(limite);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void ExcluirPorID(String _id) throws SQLException {
		
		LimiteFluxo limite = PegarPeloID(_id);
		
		Excluir(limite);

	}

	@Override
	public LimiteFluxo PegarPeloID(String _id) throws SQLException {
		
		LimiteFluxo limite = entityManager.find(LimiteFluxo.class, _id);
		
		return limite;
	}

	@Override
	public List<LimiteFluxo> listarTodos() throws SQLException {
		
		Query q = entityManager.createQuery("from LimiteFluxo r", LimiteFluxo.class);
		
		@SuppressWarnings("unchecked")
		List<LimiteFluxo> limites = q.getResultList();
		
		return limites;
	}

	@Override
	public boolean ExisteLimiteFluxo(String _id) throws SQLException {
		
		LimiteFluxo limite = entityManager.find(LimiteFluxo.class, _id);
		
		if (limite != null) {
			return true;
		}
		else {
			return false;
		}
	}

}
