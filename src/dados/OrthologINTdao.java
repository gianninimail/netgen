package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.Ortholog;

public interface OrthologINTdao {

void Inserir(Ortholog _ortholog) throws SQLException;
	
	void Editar(Ortholog _ortholog) throws SQLException;

	void Excluir(Ortholog _ortholog) throws SQLException;
	
	void ExcluirPorID(final String _id) throws SQLException;

	Ortholog PegarPeloID(String _id) throws SQLException;

	Long PegarID() throws SQLException;
	
	List<Ortholog> listarTodos() throws SQLException;
}