package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.Reacao;

public interface ReacaoINTdao {

	void Inserir(Reacao _reaction) throws SQLException;
	
	void Editar(Reacao _reaction) throws SQLException;

	void Excluir(Reacao _reaction) throws SQLException;
	
	void ExcluirPorID(String abbreviation) throws SQLException;

	Reacao PegarPeloID(String abbreviation) throws SQLException;
	
	List<Reacao> listarTodos() throws SQLException;

	boolean ExisteReacao(String abbreviation) throws SQLException;
}
