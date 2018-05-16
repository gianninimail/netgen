package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Reconstrucao;

public class ReconstrucaoDAO implements ReconstrucaoINTdao {

	private static ReconstrucaoDAO instancia;
	protected EntityManager entityManager;
	
	public ReconstrucaoDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static ReconstrucaoDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new ReconstrucaoDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(Reconstrucao _reconstrucao) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_reconstrucao);
		
		this.entityManager.getTransaction().commit();
	}

	@Override
	public void Editar(Reconstrucao _reconstrucao) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_reconstrucao);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(Reconstrucao _reconstrucao) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		Reconstrucao reconstrucao = entityManager.find(Reconstrucao.class, _reconstrucao.getId());
		
		this.entityManager.remove(reconstrucao);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void ExcluirPorID(Long _id) throws SQLException {

		Reconstrucao reconstrucao = PegarPeloID(_id);
		
		Excluir(reconstrucao);

	}

	@Override
	public Reconstrucao PegarPeloID(Long _id) throws SQLException {

		Reconstrucao reconstrucao = entityManager.find(Reconstrucao.class, _id);
		
		return reconstrucao;
	}

	@Override
	public List<Reconstrucao> listarTodos() throws SQLException {

		Query q = entityManager.createQuery("from Reconstrucao r", Reconstrucao.class);
		
		@SuppressWarnings("unchecked")
		List<Reconstrucao> reconstrucaos = q.getResultList();
		
		return reconstrucaos;
	}

	@Override
	public boolean ExisteReconstrucao(Long _id) throws SQLException {

		Reconstrucao reconstrucao = entityManager.find(Reconstrucao.class, _id);
		
		if (reconstrucao != null) {
			return true;
		}
		else {
			return false;
		}
	}

}
