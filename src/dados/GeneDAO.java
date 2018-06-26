package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.Gene;

public class GeneDAO implements GeneINTdao {

	private static GeneDAO instancia;
	protected EntityManager entityManager;
	
	public GeneDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static GeneDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new GeneDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(Gene _gene) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_gene);
		
		this.entityManager.getTransaction().commit();

	}
	
	@Override
	public void Editar(Gene _gene) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_gene);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(Gene _gene) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		Gene gene = entityManager.find(Gene.class, _gene.getId());
		
		this.entityManager.remove(gene);
		
		this.entityManager.getTransaction().commit();

	}
	
	@Override
	public void ExcluirPorID(String _id) throws SQLException {
		
		Gene gene = PegarPeloID(_id);
		
		Excluir(gene);
		
	}

	@Override
	public Gene PegarPeloID(String _id) throws SQLException {
		
		Gene gene = entityManager.find(Gene.class, _id);
		
		return gene;
	}

	@Override
	public Long PegarID() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Gene> listarTodos() throws SQLException {
		
		@SuppressWarnings("unchecked")
		List<Gene> genes = entityManager.createQuery("FROM " + Gene.class.getName()).getResultList();
		
		return genes;
	}

	@Override
	public boolean ExisteGene(String _id) throws SQLException {

		Gene gene = entityManager.find(Gene.class, _id);
		
		if (gene != null) {
			return true;
		}
		else {
			return false;
		}
	}

}
