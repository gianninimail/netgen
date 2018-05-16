package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.Componente;

public interface ComponenteINTdao {

	void Inserir(Componente _compound) throws SQLException;
	
	void Editar(Componente _compound) throws SQLException;

	void Excluir(Componente _compound) throws SQLException;
	
	void ExcluirPorID(String abbreviation) throws SQLException;

	Componente PegarPeloID(String abbreviation) throws SQLException;
	
	List<Componente> listarTodos() throws SQLException;

	boolean ExisteCompound(String abbreviation) throws SQLException;
}
