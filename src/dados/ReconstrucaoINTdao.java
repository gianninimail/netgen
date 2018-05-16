package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.Reconstrucao;

public interface ReconstrucaoINTdao {

	void Inserir(Reconstrucao _reconstrucao) throws SQLException;
	
	void Editar(Reconstrucao _reconstrucao) throws SQLException;

	void Excluir(Reconstrucao _reconstrucao) throws SQLException;
	
	void ExcluirPorID(Long _id) throws SQLException;

	Reconstrucao PegarPeloID(Long _id) throws SQLException;
	
	List<Reconstrucao> listarTodos() throws SQLException;

	boolean ExisteReconstrucao(Long _id) throws SQLException;
}
