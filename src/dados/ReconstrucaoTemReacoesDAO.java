package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.ReconstrucaoTemReacoes;

public class ReconstrucaoTemReacoesDAO implements ReconstrucaoTemReacoesINTdao {

	private static ReconstrucaoTemReacoesDAO instancia;
	protected EntityManager entityManager;
	
	public ReconstrucaoTemReacoesDAO() {
		this.entityManager = getEntityManager();
	}
	
	private EntityManager getEntityManager() {
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");
		
		if (this.entityManager == null) {
			
			this.entityManager = fabrica.createEntityManager();
		}
		
		return this.entityManager;
	}
	
	public static ReconstrucaoTemReacoesDAO getInstancia() {
		
		if (instancia == null) {
			
			instancia = new ReconstrucaoTemReacoesDAO();
		}
		
		return instancia;
	}
	
	@Override
	public void Inserir(ReconstrucaoTemReacoes _rhr) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.persist(_rhr);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Editar(ReconstrucaoTemReacoes _rhr) throws SQLException {

		this.entityManager.getTransaction().begin();
		
		this.entityManager.merge(_rhr);
		
		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(ReconstrucaoTemReacoes _rhr) throws SQLException {
		// TODO Auto-generated method stub		
	}

	@Override
	public void ExcluirPorID(Long _idReconstrucao, String _abbreviationR) throws SQLException {
		
		ReconstrucaoTemReacoes rhr = PegarPeloID(_idReconstrucao, _abbreviationR);
		
		Excluir(rhr);

	}

	@Override
	public ReconstrucaoTemReacoes PegarPeloID(Long _idReconstrucao, String _abbreviationR) throws SQLException {

		//ReconstrucaoTemReacoes rhr = null;//entityManager.find(ReconstrucaoTemReacoes.class, _abbreviationR);
		
		Query query = entityManager.createQuery("SELECT o FROM ReconstrucaoTemReacoes o WHERE o.reacao.abbreviation = :idRea and o.reconstrucao.id = :idRec");
		query.setParameter("idRec", _idReconstrucao);
		query.setParameter("idRea", _abbreviationR);
		
		//ReconstrucaoTemReacoes rhr = (ReconstrucaoTemReacoes)query.getSingleResult();
		
		@SuppressWarnings("unchecked")
		List<ReconstrucaoTemReacoes> rhrs = query.getResultList();
		
		if (!rhrs.isEmpty()) {
			return rhrs.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public List<ReconstrucaoTemReacoes> listarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ExisteReconstrucaoTemReacoes(Long _idReconstrucao, String _abbreviationR) throws SQLException {
		
		Query query = entityManager.createQuery("SELECT o FROM ReconstrucaoTemReacoes o WHERE o.reacao.abbreviation = :idRea and o.reconstrucao = :idRec");
		query.setParameter("idRec", _idReconstrucao);
		query.setParameter("idRea", _abbreviationR);
		
		//ReconstrucaoTemReacoes rhr = (ReconstrucaoTemReacoes)query.getSingleResult();
		
		@SuppressWarnings("unchecked")
		List<ReconstrucaoTemReacoes> rhrs = query.getResultList();
		
		if (!rhrs.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

}
