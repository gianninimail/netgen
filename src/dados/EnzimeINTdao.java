package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.Enzima;

public interface EnzimeINTdao {

	void Inserir(Enzima _enzime) throws SQLException;
	
	void Editar(Enzima _enzime) throws SQLException;

	void Excluir(Enzima _enzime) throws SQLException;
	
	void ExcluirPorID(String _ec) throws SQLException;

	Enzima PegarPeloID(String _ec) throws SQLException;
	
	List<Enzima> listarTodos() throws SQLException;

	boolean ExisteEnzime(String _ec) throws SQLException;
}
