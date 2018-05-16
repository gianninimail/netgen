package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.ReconstrucaoTemReacoes;

public interface ReconstrucaoTemReacoesINTdao {

	void Inserir(ReconstrucaoTemReacoes _rhr) throws SQLException;
	
	void Editar(ReconstrucaoTemReacoes _rhr) throws SQLException;

	void Excluir(ReconstrucaoTemReacoes _rhr) throws SQLException;
	
	void ExcluirPorID(Long _idReconstrucao, String _abbreviationR) throws SQLException;

	ReconstrucaoTemReacoes PegarPeloID(Long _idReconstrucao, String _abbreviationR) throws SQLException;
	
	List<ReconstrucaoTemReacoes> listarTodos() throws SQLException;

	boolean ExisteReconstrucaoTemReacoes(Long _idReconstrucao, String _abbreviationR) throws SQLException;
}
