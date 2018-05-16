package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Reacao;

public class ReacaoDAO implements ReacaoINTdao {

	private static ReacaoDAO instancia;
	protected EntityManager entityManager;
	
	public ReacaoDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static ReacaoDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new ReacaoDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(Reacao _reaction) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_reaction);
		
		this.entityManager.getTransaction().commit();

	}
	
	@Override
	public void Editar(Reacao _reaction) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_reaction);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(Reacao _reaction) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		Reacao reaction = entityManager.find(Reacao.class, _reaction.getAbbreviation());
		
		this.entityManager.remove(reaction);
		
		this.entityManager.getTransaction().commit();

	}
	
	@Override
	public void ExcluirPorID(String _abbreviation) throws SQLException {
		
		Reacao reaction = PegarPeloID(_abbreviation);
		
		Excluir(reaction);
		
	}

	@Override
	public Reacao PegarPeloID(String abbreviation) throws SQLException {
		
		Reacao reaction = entityManager.find(Reacao.class, abbreviation);
		
		return reaction;
	}

	@Override
	public List<Reacao> listarTodos() throws SQLException {
		
		Query q = entityManager.createQuery("from Reacao r", Reacao.class);
		
		@SuppressWarnings("unchecked")
		List<Reacao> reactions = q.getResultList();
		
		return reactions;
	}
	
	@Override
	public boolean ExisteReacao(String abbreviation) throws SQLException {
		
		Reacao reaction = entityManager.find(Reacao.class, abbreviation);
		
		if (reaction != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
