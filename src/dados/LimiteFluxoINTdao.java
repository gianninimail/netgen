package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.LimiteFluxo;

public interface LimiteFluxoINTdao {

void Inserir(LimiteFluxo _limite) throws SQLException;
	
	void Editar(LimiteFluxo _limite) throws SQLException;

	void Excluir(LimiteFluxo _limite) throws SQLException;
	
	void ExcluirPorID(String _id) throws SQLException;

	LimiteFluxo PegarPeloID(String _id) throws SQLException;
	
	List<LimiteFluxo> listarTodos() throws SQLException;

	boolean ExisteLimiteFluxo(String _id) throws SQLException;
}
