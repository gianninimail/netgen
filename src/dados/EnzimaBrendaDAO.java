package dados;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.EnzimaBrenda;

public class EnzimaBrendaDAO implements EnzimaBrendaINTdao {

	private static EnzimaBrendaDAO instancia;
	protected EntityManager entityManager;

	public EnzimaBrendaDAO() {
		this.entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {

		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("ccbh4851System");

		if (this.entityManager == null) {

			this.entityManager = fabrica.createEntityManager();
		}

		return this.entityManager;
	}

	public static EnzimaBrendaDAO getInstancia() {

		if (instancia == null) {

			instancia = new EnzimaBrendaDAO();
		}

		return instancia;
	}

	@Override
	public void Inserir(EnzimaBrenda _enzimaBrenda) throws SQLException {

		this.entityManager.getTransaction().begin();

		this.entityManager.persist(_enzimaBrenda);

		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Editar(EnzimaBrenda _enzimaBrenda) throws SQLException {

		this.entityManager.getTransaction().begin();

		this.entityManager.merge(_enzimaBrenda);

		this.entityManager.getTransaction().commit();

	}

	@Override
	public void Excluir(EnzimaBrenda _enzimaBrenda) throws SQLException {

		this.entityManager.getTransaction().begin();

		EnzimaBrenda enzimaBrenda = entityManager.find(EnzimaBrenda.class, _enzimaBrenda.getEc_number());

		this.entityManager.remove(enzimaBrenda);

		this.entityManager.getTransaction().commit();

	}

	@Override
	public void ExcluirPorID(String _id) throws SQLException {

		EnzimaBrenda enzimaBrenda = PegarPeloID(_id);

		Excluir(enzimaBrenda);

	}

	@Override
	public EnzimaBrenda PegarPeloID(String _id) throws SQLException {

		EnzimaBrenda enzimaBrenda = entityManager.find(EnzimaBrenda.class, _id);

		return enzimaBrenda;
	}
	
	@Override
	public boolean ExisteEnzimaBrenda(String _id) throws SQLException {

		EnzimaBrenda enzimaBrenda = entityManager.find(EnzimaBrenda.class, _id);

		if (enzimaBrenda != null) {
			
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public List<EnzimaBrenda> listarTodos() throws SQLException {

		@SuppressWarnings("unchecked")
		List<EnzimaBrenda> enzimaBrendas = entityManager.createQuery("FROM " + EnzimaBrenda.class.getName())
				.getResultList();

		return enzimaBrendas;
	}

}
