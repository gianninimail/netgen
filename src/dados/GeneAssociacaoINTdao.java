package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.GeneAssociacao;

public interface GeneAssociacaoINTdao {

	void Inserir(GeneAssociacao _geneAssociacao) throws SQLException;
	
	void Editar(GeneAssociacao _geneAssociacao) throws SQLException;

	void Excluir(GeneAssociacao _geneAssociacao) throws SQLException;
	
	void ExcluirPorID(String _id) throws SQLException;

	GeneAssociacao PegarPeloID(String _id) throws SQLException;
	
	List<GeneAssociacao> listarTodos() throws SQLException;

	boolean ExisteGeneAssociacao(String _id) throws SQLException;

	List<GeneAssociacao> obterGenesAssociacaoPorIdReacao(String _abbreviationR) throws SQLException;
}
