package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.Proteina;

public interface ProteinaINTdao {

	void Inserir(Proteina _proteina) throws SQLException;
	
	void Editar(Proteina _proteina) throws SQLException;

	void Excluir(Proteina _proteina) throws SQLException;
	
	void ExcluirPorID(String _id) throws SQLException;

	Proteina PegarPeloID(String _id) throws SQLException;
	
	List<Proteina> listarTodos() throws SQLException;

	boolean ExisteProteina(String _id) throws SQLException;
}
