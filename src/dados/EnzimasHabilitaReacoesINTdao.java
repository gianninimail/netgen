package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.EnzimasHabilitaReacoes;

public interface EnzimasHabilitaReacoesINTdao {

	void Inserir(EnzimasHabilitaReacoes _ehr) throws SQLException;
	
	void Editar(EnzimasHabilitaReacoes _ehr) throws SQLException;

	void Excluir(EnzimasHabilitaReacoes _ehr) throws SQLException;
	
	void ExcluirPorID(String _reaction_id, String _enzime_id) throws SQLException;

	EnzimasHabilitaReacoes PegarPeloID(String _reaction_id, String _enzime_id) throws SQLException;
	
	List<EnzimasHabilitaReacoes> listarTodos() throws SQLException;

	boolean ExisteEnzime(String _reaction_id, String _enzime_id, String _proteina_id) throws SQLException;

	boolean ExisteEnzime(EnzimasHabilitaReacoes _EHR) throws SQLException;
}
