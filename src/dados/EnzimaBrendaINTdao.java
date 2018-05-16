package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.EnzimaBrenda;

public interface EnzimaBrendaINTdao {

void Inserir(EnzimaBrenda _enzimaBrenda) throws SQLException;
	
	void Editar(EnzimaBrenda _enzimaBrenda) throws SQLException;

	void Excluir(EnzimaBrenda _enzimaBrenda) throws SQLException;
	
	void ExcluirPorID(final String _id) throws SQLException;

	EnzimaBrenda PegarPeloID(String _id) throws SQLException;
	
	List<EnzimaBrenda> listarTodos() throws SQLException;

	boolean ExisteEnzimaBrenda(String _id) throws SQLException;
}
