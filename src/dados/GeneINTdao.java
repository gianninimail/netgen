package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.Gene;

public interface GeneINTdao {

	void Inserir(Gene _gene) throws SQLException;
	
	void Editar(Gene _gene) throws SQLException;

	void Excluir(Gene _gene) throws SQLException;
	
	void ExcluirPorID(final Long _id) throws SQLException;

	Gene PegarPeloID(Long _id) throws SQLException;

	Long PegarID() throws SQLException;
	
	List<Gene> listarTodos() throws SQLException;
}
