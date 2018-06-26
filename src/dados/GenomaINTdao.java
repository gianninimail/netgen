package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.Genoma;

public interface GenomaINTdao {

	void Inserir(Genoma _genoma) throws SQLException;
	
	void Editar(Genoma _genoma) throws SQLException;

	void Excluir(Genoma _genoma) throws SQLException;
	
	void ExcluirPorID(final String _id) throws SQLException;

	Genoma PegarPeloID(String _id) throws SQLException;

	Long PegarID() throws SQLException;
	
	List<Genoma> listarTodos() throws SQLException;
}
