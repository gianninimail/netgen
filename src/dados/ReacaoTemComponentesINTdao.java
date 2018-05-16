package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.ReacaoTemComponentes;

public interface ReacaoTemComponentesINTdao {

	void Inserir(ReacaoTemComponentes _rhc) throws SQLException;
	
	void Editar(ReacaoTemComponentes _rhc) throws SQLException;

	void Excluir(ReacaoTemComponentes _rhc) throws SQLException;
	
	void ExcluirPorID(String _abbreviationR, String _abbreviationC) throws SQLException;

	ReacaoTemComponentes PegarPeloID(String _abbreviationR, String _abbreviationC) throws SQLException;
	
	List<ReacaoTemComponentes> listarTodos() throws SQLException;

	boolean ExisteReacaoTemComponentes(String _abbreviationR, String _abbreviationC, char _tipo_P_ou_R) throws SQLException;
}
