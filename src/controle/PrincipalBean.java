package controle;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
//import javax.faces.model.ListDataModel;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;
import org.sbml.jsbml.Reaction;

import entidades.Usuario;
import util.JSFUtil;
import util.SessaoUtil;

@ManagedBean
@ViewScoped
public class PrincipalBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//----------------INICIO DOS ATRIBUTOS-----------------------
	private String pathAreTrabalho = "/ccbh4851system/";
	//private String nomeArquivoFASTA = "saidaFasta.fasta";
	private String nomeArquivoSaidaSBML = "CCBH4851_v1.xml";
	private String nomeArquivoGBK = "CCBH4851.gb";
	private String nomeArquivoBaseSBML = "iPAE1146.xml";
	//private String nomeArquivoAjustadoFASTA = "fj";
	
	private String nomeArquivoTabID = "tabelaNCBI.txt";
	
	private String tituloRede = "Reconstrução completa";
	private String versaoRede = "1";
	
	private Usuario user;
	
	private String textoSaida = "Press the \"New Reconstruction\" button to start a new run\n\n\n";

	private String idReacaoBiomassa = "R_PAO1_Biomass";
	
	private boolean inferirGenes = true;
	
	private boolean estaAbaconfirmar = false;
	
	private List<String> listaReacoesDiponiveis;
	private List<String> listaReactionsManuais = new ArrayList<String>();
	
	private DualListModel<String> reacoes;
	
	Reaction selectedReacrion;
	
	UploadedFile arqGBK;
	
	UploadedFile arqSBMLbase;
	
	//--------------------FIM DOS ATRIBUTOS--------------------

	public PrincipalBean() {
		this.user = new Usuario();
		this.textoSaida = "";
	}

	
	//----------INICIO DOS METODOS GETS E SETS----------------
	public String getTextoSaida() {
		return textoSaida;
	}

	public void setTextoSaida(String textoSaida) {
		this.textoSaida = textoSaida;
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	/*
	public String getNomeArquivoFASTA() {
		return nomeArquivoFASTA;
	}

	public void setNomeArquivoFASTA(String nomeArquivoFASTA) {
		this.nomeArquivoFASTA = nomeArquivoFASTA;
	}
	*/

	public String getNomeArquivoGBK() {
		return nomeArquivoGBK;
	}

	public void setNomeArquivoGBK(String nomeArquivoGBK) {
		this.nomeArquivoGBK = nomeArquivoGBK;
	}

	/*
	public String getNomeArquivoAjustadoFASTA() {
		return nomeArquivoAjustadoFASTA;
	}

	public void setNomeArquivoAjustadoFASTA(String nomeArquivoAjustadoFASTA) {
		this.nomeArquivoAjustadoFASTA = nomeArquivoAjustadoFASTA;
	}
	*/

	public String getNomeArquivoTabID() {
		return nomeArquivoTabID;
	}

	public void setNomeArquivoTabID(String nomeArquivoTabID) {
		this.nomeArquivoTabID = nomeArquivoTabID;
	}

	public String getTituloRede() {
		return tituloRede;
	}

	public void setTituloRede(String tituloRede) {
		this.tituloRede = tituloRede;
	}

	public String getVersaoRede() {
		return versaoRede;
	}

	public void setVersaoRede(String versaoRede) {
		this.versaoRede = versaoRede;
	}
	
	public String getIdReacaoBiomassa() {
		return idReacaoBiomassa;
	}

	public void setIdReacaoBiomassa(String idReacaoBiomassa) {
		this.idReacaoBiomassa  = idReacaoBiomassa;
	}

	public String getNomeArquivoSaidaSBML() {
		return nomeArquivoSaidaSBML;
	}

	public void setNomeArquivoSaidaSBML(String nomeArquivoSaidaSBML) {
		
		if (nomeArquivoSaidaSBML.toLowerCase().contains(".xml") || nomeArquivoSaidaSBML.toLowerCase().contains(".sbml")) {
			this.nomeArquivoSaidaSBML = nomeArquivoSaidaSBML;
		}
		else {
			
			this.nomeArquivoSaidaSBML = nomeArquivoSaidaSBML + ".xml";
		}
	}

	public String getNomeArquivoBaseSBML() {
		return nomeArquivoBaseSBML;
	}

	public void setNomeArquivoBaseSBML(String nomeArquivoBaseSBML) {
		this.nomeArquivoBaseSBML = nomeArquivoBaseSBML;
	}
	
	public Reaction getSelectedReacrion() {
		return selectedReacrion;
	}

	public void setSelectedReacrion(Reaction selectedReacrion) {
		this.selectedReacrion = selectedReacrion;
	}

	public boolean isInferirGenes() {
		return inferirGenes;
	}

	public void setInferirGenes(boolean inferirGenes) {
		this.inferirGenes = inferirGenes;
	}

	public DualListModel<String> getReacoes() {
		return reacoes;
	}

	public void setReacoes(DualListModel<String> reacoes) {
		this.reacoes = reacoes;
	}

	public UploadedFile getArqGBK() {
		return arqGBK;
	}


	public void setArqGBK(UploadedFile arqGBK) {
		this.arqGBK = arqGBK;
	}


	public UploadedFile getArqSBMLbase() {
		return arqSBMLbase;
	}


	public void setArqSBMLbase(UploadedFile arqSBMLbase) {
		this.arqSBMLbase = arqSBMLbase;
	}
	
	public boolean isEstaAbaconfirmar() {
		return estaAbaconfirmar;
	}


	public void setEstaAbaconfirmar(boolean estaAbaconfirmar) {
		this.estaAbaconfirmar = estaAbaconfirmar;
	}
	
	//----------FIM DOS METODOS GETS E SETS----------------
	
	public void ExecutarEmSegundoPlano() {
		
		try {
			this.textoSaida += "New Reconstruction started...."
					+ "\nAt the end, you will receive an email with all the information along with the SBML!"
					+ "\nSee you later!!!"
					+ "\n========================================================================================"
					+ "\n\n\n";
			
			//Thread thread = new Thread(new ReconstrucaoBean(tituloRede, versaoRede, user, nomeArquivoSBML, this.nomeArquivoGBK, nomeArquivoBase, nomeArquivoFASTA, nomeArquivoAjustadoFASTA, nomeArquivoTabID));// onde este numero é o intervalo de tempo ai voce controla este tempo aki
			
			this.user = SessaoUtil.pegarUsuarioSessao();
			
			if(this.inferirGenes) {
				this.listaReactionsManuais.addAll(reacoes.getTarget());
			}
			
			Thread thread = new Thread(new ReconstrucaoBean(tituloRede, versaoRede, user, 
					nomeArquivoSaidaSBML, 
					nomeArquivoGBK, 
					nomeArquivoBaseSBML, 
					//nomeArquivoFASTA, 
					//nomeArquivoAjustadoFASTA, 
					nomeArquivoTabID,
					this.inferirGenes,
					idReacaoBiomassa,
					this.listaReactionsManuais));
			
			thread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void executarFileUploadGBK(FileUploadEvent event) {
 
		try {
			
			if (!(new File(event.getFile().getFileName()).exists())) {
				
				String pathGBK = event.getFile().getFileName();
				
				this.arqGBK = event.getFile();
			
				this.arqGBK.write(this.pathAreTrabalho + pathGBK);
				
				this.nomeArquivoGBK = pathGBK;
				
				JSFUtil.adicionarMensagemSucesso("File uploaded with success!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	public void executarFileUploadSBML(FileUploadEvent event) {
		 
		try {
			
			if (!(new File(event.getFile().getFileName()).exists())) {
				
				String pathSBML = event.getFile().getFileName();
				
				this.arqSBMLbase = event.getFile();
			
				this.arqSBMLbase.write(this.pathAreTrabalho + pathSBML);
				
				this.nomeArquivoBaseSBML = pathSBML;
				
				JSFUtil.adicionarMensagemSucesso("File uploaded with success!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	public String onFlowProcess(FlowEvent event) {
        /*
		if (!(new File(this.arqGBK.getFileName()).exists())) {
			executarFileUploadGBK();
		}
		
		if (!(new File(this.arqSBMLbase.getFileName()).exists())) {
			executarFileUploadSBML();
		}
		*/
		if(!this.inferirGenes) {
        	
			this.inferirGenes = false;   //reset in case user goes back
            
        	if (event.getOldStep().equals("confirm")) {
        		//this.estaAbaconfirmar = false;
				return "auto";
			}
        	
        	if (this.reacoes != null) {
        		this.reacoes.getTarget().clear();
			}
        	//this.estaAbaconfirmar = true;
        	return "confirm";
        }
        else {
        	
        	if (event.getNewStep().equals("inferencia")) {
				CarregarReacoes();
			}
        	//this.estaAbaconfirmar = false;
        	return event.getNewStep();
        }
    }
	
	private void CarregarReacoes() {
		try {
			
			if (this.listaReacoesDiponiveis == null || this.listaReacoesDiponiveis.isEmpty()) {
				
				JsbmlBeanV2L1 j21 = new JsbmlBeanV2L1(this.pathAreTrabalho + this.nomeArquivoBaseSBML);
				
				this.listaReacoesDiponiveis = j21.obterListaReactionDoModelo();
				
				//-----------GENES INFERIDOS MÁRCIO---------//
				listaReactionsManuais.add("R_rxn02476");
				listaReactionsManuais.add("R_rxn00695");
				listaReactionsManuais.add("R_ATPM");
				
				listaReactionsManuais.add("R_rxn05468");//VERIFICAR
				
				listaReacoesDiponiveis.removeAll(listaReactionsManuais);
				//------------------------------------------
				
				/*
				listaReactionsManuais.add("R_rPY00182");
				listaReactionsManuais.add("R_rPY00180");
				listaReactionsManuais.add("R_rPY00166");
				listaReactionsManuais.add("R_rPY00171");
				listaReactionsManuais.add("R_rPY00174");
				listaReactionsManuais.add("R_rPY00177");
				listaReactionsManuais.add("R_rxn01268");
				listaReactionsManuais.add("R_rxn01729");
				listaReactionsManuais.add("R_rxn05064");
				listaReactionsManuais.add("R_rxn05467");
				listaReactionsManuais.add("R_rxn05319");
				listaReactionsManuais.add("R_rxn10577");
				listaReactionsManuais.add("R_rxn13789");
				listaReactionsManuais.add("R_rxn00420");
				*/
				
				this.reacoes = new DualListModel<>(listaReacoesDiponiveis, listaReactionsManuais);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CancelarReconstrucao() {
		
		try {
			
			this.arqGBK = null;
			
			this.arqSBMLbase = null;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	public void init() {
		
		//reacoes = new DualListModel<String>(this.listaReacoesDiponiveis , this.listaReactionsManuais);
	}
}
