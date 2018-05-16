package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Ortholog;

public class OrthologDAO implements OrthologINTdao {

	private static OrthologDAO instancia;
	protected EntityManager entityManager;
	
	public OrthologDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("orthomcl");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static OrthologDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new OrthologDAO();
		}
		
		return instancia;
	}	
	
	@Override
	public void Inserir(Ortholog _ortholog) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_ortholog);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Editar(Ortholog _ortholog) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_ortholog);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(Ortholog _ortholog) throws SQLException {
		
		this.entityManager.getTransaction().begin();
		
		Ortholog ortholog = entityManager.find(Ortholog.class, _ortholog.getSequenceIdA());
		
		this.entityManager.remove(ortholog);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void ExcluirPorID(String _id) throws SQLException {

		Ortholog ortholog = PegarPeloID(_id);
		
		Excluir(ortholog);

	}

	@Override
	public Ortholog PegarPeloID(String _id) throws SQLException {
		
		Ortholog ortholog = entityManager.find(Ortholog.class, _id);
		
		return ortholog;
	}

	@Override
	public Long PegarID() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ortholog> listarTodos() throws SQLException {
		
		Query q = entityManager.createQuery("from Ortholog o", Ortholog.class);
		
		@SuppressWarnings("unchecked")
		List<Ortholog> orthologs = q.getResultList();
		
		return orthologs;
	}

}