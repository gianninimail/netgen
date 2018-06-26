package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.EnzimasHabilitaReacoes;

public class EnzimasHabilitaReacoesDAO implements EnzimasHabilitaReacoesINTdao {

	private static EnzimasHabilitaReacoesDAO instancia;
	protected EntityManager entityManager;
	
	public EnzimasHabilitaReacoesDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static EnzimasHabilitaReacoesDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new EnzimasHabilitaReacoesDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(EnzimasHabilitaReacoes _ehr) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_ehr);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Editar(EnzimasHabilitaReacoes _ehr) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_ehr);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(EnzimasHabilitaReacoes _ehr) throws SQLException {
		
		/*
		this.entityManager.getTransaction().begin();
		
		EnzimasHabilitaReacoes ehr = entityManager.find(EnzimasHabilitaReacoes.class, _ehr.getId());
		
		this.entityManager.remove(ehr);
		
		this.entityManager.getTransaction().commit();
		*/
	}

	@Override
	public void ExcluirPorID(String _reaction_id, String _enzime_id) throws SQLException {

		EnzimasHabilitaReacoes ehr = PegarPeloID(_reaction_id, _enzime_id);
		
		Excluir(ehr);

	}

	@Override
	public EnzimasHabilitaReacoes PegarPeloID(String _reaction_id, String _enzime_id) throws SQLException {

		EnzimasHabilitaReacoes ehr = entityManager.find(EnzimasHabilitaReacoes.class, _reaction_id);
		
		return ehr;
	}

	@Override
	public List<EnzimasHabilitaReacoes> listarTodos() throws SQLException {

		Query q = entityManager.createQuery("from Compound r", EnzimasHabilitaReacoes.class);
		
		@SuppressWarnings("unchecked")
		List<EnzimasHabilitaReacoes> ehrs = q.getResultList();
		
		return ehrs;
	}

	@Override
	public boolean ExisteEnzime(String _reaction_id, String _enzime_id, String _proteina_id) throws SQLException {

		Query query = entityManager.createQuery("SELECT o FROM EnzimasHabilitaReacoes o WHERE o.reacao.abbreviation = :idR and o.enzime.ec_number = :idE and o.proteina.id = :idP");
		query.setParameter("idR", _reaction_id);
		query.setParameter("idE", _enzime_id);
		query.setParameter("idP", _proteina_id);
		
		@SuppressWarnings("unchecked")
		List<EnzimasHabilitaReacoes> ehrs = query.getResultList();
		
		if (!ehrs.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean ExisteEnzime(EnzimasHabilitaReacoes _EHR) throws SQLException {

		Query query = entityManager.createQuery("SELECT o FROM EnzimasHabilitaReacoes o WHERE o.reacao.abbreviation = :idR and o.enzima.ec_number = :idE and o.proteina.id = :idP");
		query.setParameter("idR", _EHR.getReacao().getAbbreviation());
		query.setParameter("idE", _EHR.getEnzima().getEc_number());
		query.setParameter("idP", _EHR.getProteina().getId());
		
		@SuppressWarnings("unchecked")
		List<EnzimasHabilitaReacoes> ehrs = query.getResultList();
		
		if (!ehrs.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

}
