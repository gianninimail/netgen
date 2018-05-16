package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.GeneAssociacao;

public class GeneAssociacaoDAO implements GeneAssociacaoINTdao {

	private static GeneAssociacaoDAO instancia;
	protected EntityManager entityManager;
	
	public GeneAssociacaoDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static GeneAssociacaoDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new GeneAssociacaoDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(GeneAssociacao _geneAssociacao) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_geneAssociacao);
		
		this.entityManager.getTransaction().commit();
		
	}

	@Override
	public void Editar(GeneAssociacao _geneAssociacao) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_geneAssociacao);
		
		this.entityManager.getTransaction().commit();
		
	}

	@Override
	public void Excluir(GeneAssociacao _geneAssociacao) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		GeneAssociacao geneAssociacao = entityManager.find(GeneAssociacao.class, _geneAssociacao.getId());
		
		this.entityManager.remove(geneAssociacao);
		
		this.entityManager.getTransaction().commit();
		
	}

	@Override
	public void ExcluirPorID(String _id) throws SQLException {

		GeneAssociacao geneAssociacao = PegarPeloID(_id);
		
		Excluir(geneAssociacao);
		
	}

	@Override
	public GeneAssociacao PegarPeloID(String _id) throws SQLException {

		GeneAssociacao geneAssociacao = entityManager.find(GeneAssociacao.class, _id);
		
		return geneAssociacao;
	}

	@Override
	public List<GeneAssociacao> listarTodos() throws SQLException {
		
		Query q = entityManager.createQuery("from GeneAssociacao ga", GeneAssociacao.class);
		
		@SuppressWarnings("unchecked")
		List<GeneAssociacao> geneAssociacaos = q.getResultList();
		
		return geneAssociacaos;
	}

	@Override
	public boolean ExisteGeneAssociacao(String _id) throws SQLException {
		
		GeneAssociacao geneAssociacao = entityManager.find(GeneAssociacao.class, _id);
		
		if (geneAssociacao != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public List<GeneAssociacao> obterGenesAssociacaoPorIdReacao(String _abbreviationR) throws SQLException {
		
		Query query = entityManager.createQuery("SELECT o FROM GeneAssociacao o WHERE o.reacao.abbreviation = :idR");
		query.setParameter("idR", _abbreviationR);
		
		@SuppressWarnings("unchecked")
		List<GeneAssociacao> geneAssociacaos = query.getResultList();
		
		return geneAssociacaos;
		
	}

}
