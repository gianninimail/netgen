package controle;

import java.io.Serializable;

import dados.ComponenteDAO;
import dados.ReacaoDAO;
import entidades.Componente;
import entidades.Reacao;

public class BCBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean inserirComponenteNoBC(Componente _compound) {
		try {
			
			ComponenteDAO daoComp = new ComponenteDAO();
			
			if (!daoComp.ExisteCompound(_compound.getAbbreviation())) {
				
				daoComp.Inserir(_compound);
				
				return true;
			}
			
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Componente obterComponenteDoBC(String _abbreviation) {
		try {
			
			ComponenteDAO daoComp = new ComponenteDAO();
			
			Componente comp = daoComp.PegarPeloID(_abbreviation);
			
			if (comp != null) {
				
				return comp;
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean inserirReacaoNoBC(Reacao _reaction) {
		try {
			
			ReacaoDAO daoReac = new ReacaoDAO();
			
			if (!daoReac.ExisteReacao(_reaction.getAbbreviation())) {
				
				daoReac.Inserir(_reaction);
				
				return true;
			}
			
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Reacao obterReacaoDoBC(String _abbreviation) {
		try {
			
			ReacaoDAO daoReac = new ReacaoDAO();
			
			Reacao reac = daoReac.PegarPeloID(_abbreviation);
			
			if (reac != null) {
				
				return reac;
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
