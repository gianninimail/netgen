package dados;

import java.sql.SQLException;
import java.util.List;

import entidades.Usuario;

public interface UsuarioINTdao {

void Inserir(Usuario _usuario) throws SQLException;
	
	void Editar(Usuario _usuario) throws SQLException;

	void Excluir(Usuario _usuario) throws SQLException;
	
	void ExcluirPorID(Long _id) throws SQLException;

	Usuario PegarPeloID(Long _id) throws SQLException;
	
	List<Usuario> listarTodos() throws SQLException;

	Usuario ExisteUsuario(String _login, String _senha) throws SQLException;
}
