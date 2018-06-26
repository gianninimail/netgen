package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.ReacaoTemComponentes;

public class ReacaoTemComponentesDAO implements ReacaoTemComponentesINTdao {

	private static ReacaoTemComponentesDAO instancia;
	protected EntityManager entityManager;
	
	public ReacaoTemComponentesDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static ReacaoTemComponentesDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new ReacaoTemComponentesDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(ReacaoTemComponentes _rhc) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_rhc);
		
		this.entityManager.getTransaction().commit();
	}

	@Override
	public void Editar(ReacaoTemComponentes _rhc) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_rhc);
		
		this.entityManager.getTransaction().commit();
		
	}

	@Override
	public void Excluir(ReacaoTemComponentes _rhc) throws SQLException {
		/*
		this.entityManager.getTransaction().begin();
		
		ReacaoTemComponentes rhc = entityManager.find(ReacaoTemComponentes.class, _rhc.getId());
		
		this.entityManager.remove(rhc);
		
		this.entityManager.getTransaction().commit();
		*/
		
	}

	@Override
	public void ExcluirPorID(String _abbreviationR, String _abbreviationC) throws SQLException {
		
		ReacaoTemComponentes rhc = PegarPeloID(_abbreviationR, _abbreviationC);
		
		Excluir(rhc);
		
	}

	@Override
	public ReacaoTemComponentes PegarPeloID(String _abbreviationR, String _abbreviationC) throws SQLException {
		
		ReacaoTemComponentes rhc = entityManager.find(ReacaoTemComponentes.class, _abbreviationR);
		
		return rhc;
	}

	@Override
	public List<ReacaoTemComponentes> listarTodos() throws SQLException {
		
		Query q = entityManager.createQuery("from Compound r", ReacaoTemComponentes.class);
		
		@SuppressWarnings("unchecked")
		List<ReacaoTemComponentes> rhcs = q.getResultList();
		
		return rhcs;
	}

	@Override
	public boolean ExisteReacaoTemComponentes(String _abbreviationR, String _abbreviationC, char _tipo_P_ou_R) throws SQLException {
		
		Query query = entityManager.createQuery("SELECT o FROM ReacaoTemComponentes o WHERE o.reacao.abbreviation = :idR and o.componente.abbreviation = :idC and o.tipo_P_ou_R = :tipo");
		query.setParameter("idR", _abbreviationR);
		query.setParameter("idC", _abbreviationC);
		query.setParameter("tipo", _tipo_P_ou_R);
		
		//ReacaoTemComponentes rhc = (ReacaoTemComponentes)query.getSingleResult();
		
		@SuppressWarnings("unchecked")
		List<ReacaoTemComponentes> rhcs = query.getResultList();
		
		if (!rhcs.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
}
