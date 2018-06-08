package controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailException;

import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.features.FeatureInterface;
import org.biojava.nbio.core.sequence.features.Qualifier;
import org.biojava.nbio.core.sequence.io.GenbankReader;
import org.biojava.nbio.core.sequence.io.GenericGenbankHeaderParser;
import org.biojava.nbio.core.sequence.io.ProteinSequenceCreator;
import org.biojava.nbio.core.sequence.template.AbstractSequence;

import entidades.Reconstrucao;
import entidades.Usuario;

import fasta.CabecalhoFASTA;
import fasta.LeitorFASTA;
import fasta.UnidFASTA;

import util.EmailUtil;

public class ReconstrucaoBean implements Serializable, Runnable {

	private static final long serialVersionUID = 1L;
	
	private static final String pathAreTrabalho = "/ccbh4851system/";
	private static final String pathOrthoPipeline = "/home/thiago/Softwares/orthomcl-pipeline/scripts/";
	
	private String logReconstrucao = "";
	
	private String nomeArquivoFASTA;
	private String nomeArquivoSaidaSBML;
	private String nomeArquivoGBK;
	private String nomeArquivoBaseSBML;
	private String nomeArquivoAjustadoFASTA;
	private String nomeArquivoTabID;
	
	private int colunaID;
	
	private String tituloRede;
	private String versaoRede;
	private Usuario user;
	
	private JsbmlBeanV2L1 jsbmlBeanV2L1;
	private JsbmlBean jsbmlBeanV3L1;
	
	private String idReacaoBiomassa;

	private Boolean inferirGenes;

	private List<String> litaIdsReactionsManuais;
	
	//private HtmlParserBean htmlParserBean;

	public ReconstrucaoBean(String _tituloRede, String _versaoRede, Usuario _user,
			String _nomeArquivoSaidaSBML, 
			String _nomeArquivoGBK, 
			String _nomeArquivoBaseSBML, 
			String _nomeArquivoFASTA, 
			String _nomeArquivoAjustadoFASTA, 
			String _nomeArquivoTabID,
			Boolean _inferirGenes,
			String _idReacaoBiomassa,
			int _colunaID,
			List<String> _litaIdsReactionsManuais) {
		
		this.tituloRede = _tituloRede;
		this.versaoRede = _versaoRede;
		this.user = _user;
		
		this.nomeArquivoFASTA = _nomeArquivoFASTA;
		this.nomeArquivoSaidaSBML = _nomeArquivoSaidaSBML;
		this.nomeArquivoBaseSBML = _nomeArquivoBaseSBML;
		this.nomeArquivoGBK = _nomeArquivoGBK;
		this.nomeArquivoAjustadoFASTA = _nomeArquivoAjustadoFASTA;
		this.nomeArquivoTabID = _nomeArquivoTabID;
		this.inferirGenes = _inferirGenes;
		this.idReacaoBiomassa = _idReacaoBiomassa;
		this.colunaID = _colunaID;
		this.litaIdsReactionsManuais = _litaIdsReactionsManuais;
		
		this.jsbmlBeanV2L1 = new JsbmlBeanV2L1(pathAreTrabalho + this.nomeArquivoBaseSBML);
		//this.htmlParserBean = new HtmlParserBean();
	}

	public ReconstrucaoBean(String _tituloRede, String _versaoRede, Usuario _user,
			String _nomeArquivoSaidaSBML, 
			String _nomeArquivoGBK, 
			String _nomeArquivoBaseSBML, 
			String _nomeArquivoTabID,
			Boolean _inferirGenes,
			String _idReacaoBiomassa,
			List<String> _litaIdsReactionsManuais) {
		
		this.tituloRede = _tituloRede;
		this.versaoRede = _versaoRede;
		this.user = _user;
		
		this.nomeArquivoSaidaSBML = _nomeArquivoSaidaSBML;
		this.nomeArquivoBaseSBML = _nomeArquivoBaseSBML;
		this.nomeArquivoGBK = _nomeArquivoGBK;
		this.nomeArquivoTabID = _nomeArquivoTabID;
		this.inferirGenes = _inferirGenes;
		this.idReacaoBiomassa = _idReacaoBiomassa;
		
		this.litaIdsReactionsManuais = _litaIdsReactionsManuais;
		
		this.jsbmlBeanV2L1 = new JsbmlBeanV2L1(pathAreTrabalho + this.nomeArquivoBaseSBML);
	}

	public void reconstruirRedeMetabolicaV2L1() {
		
		String dataInicial = new Date().toString();
		
		List<String> genesNAOachados = new ArrayList<String>();
		
		HashMap<String, String> tabNCBI = lerTabelaDePara(pathAreTrabalho + this.nomeArquivoTabID);
		
		HashMap<String, HashMap<String, String>> sequenciasGBK = lerArquivoGBK(pathAreTrabalho + this.nomeArquivoGBK);
		
		Reconstrucao rec = jsbmlBeanV2L1.novaReconstrucaoPeloSbmlV2L1(this.tituloRede, this.versaoRede, sequenciasGBK, tabNCBI, genesNAOachados, this.inferirGenes, this.idReacaoBiomassa, this.litaIdsReactionsManuais);
		
		//System.out.println(genesNAOachados.size());
		
		jsbmlBeanV2L1.gerarSBMLdeReconstrucao(rec, this.idReacaoBiomassa, pathAreTrabalho + this.nomeArquivoSaidaSBML);
		
		String dataFinal = new Date().toString();
		
		this.logReconstrucao = executarAnaliseFBA(this.nomeArquivoSaidaSBML, this.nomeArquivoSaidaSBML);
		
		EnviarEmail(dataInicial, dataFinal);
	}
	
	public void reconstruirRedeMetabolicaV3L1() {
		
		String dataInicial = new Date().toString();
		
		List<String> genesNAOachados = new ArrayList<String>();
		
		converterGBparaFASTA(pathAreTrabalho + this.nomeArquivoGBK, pathAreTrabalho + this.nomeArquivoFASTA);
		
		ajustarArquivoFastaParaAnaliseOrtologia(pathAreTrabalho + nomeArquivoFASTA, this.colunaID);
		
		executarAnaliseOrtologia(pathAreTrabalho + nomeArquivoAjustadoFASTA);
		
		Map<String, UnidFASTA> unidsFASTA = obterUnidadesFASTAdoArquivo(pathAreTrabalho + this.nomeArquivoFASTA);
		
		Reconstrucao rec = jsbmlBeanV3L1.novaReconstrucaoPeloSbmlPAO1(this.tituloRede, this.versaoRede, unidsFASTA, genesNAOachados, true);
		
		//System.out.println(genesNAOachados.size());
		
		//rec = htmlParserBean.continuarReconstrucaoPeloFasta(rec, unidsFASTA, genesNAOachados);
		
		jsbmlBeanV2L1.gerarSBMLdeReconstrucao(rec, this.idReacaoBiomassa, pathAreTrabalho + this.nomeArquivoSaidaSBML);
		
		String dataFinal = new Date().toString();
		
		EnviarEmail(dataInicial, dataFinal);
	}

	public HashMap<String, UnidFASTA> obterUnidadesFASTAdoArquivo(String _pathArquivoFASTA) {

		try {
			
			HashMap<String, UnidFASTA> mapaFASTA = new HashMap<String, UnidFASTA>();
			
			LeitorFASTA leitor = LeitorFASTA.getInstance( _pathArquivoFASTA );
			
			UnidFASTA und = leitor.readNext();
			while( und != null ) {

				String[] nomeCabecalho = ((CabecalhoFASTA)und.getCabecalhos().toArray()[0]).getName().split("\\|");
				
				String id = nomeCabecalho[0];
				
				mapaFASTA.put(id, und);
				
				und = leitor.readNext();
			}
			
			return mapaFASTA;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	private String converterGBparaFASTA(String _pathInArqGBK, String _pathOutArqFASTA) {
		try {
			
			String log = "";
			
			// SCRIPT PARA AJUSTAR FASTA
			
			String scriptPython = "python gb_para_fasta_v3.py" + " " + _pathInArqGBK + " " + _pathOutArqFASTA;
			
			File diretorio = new File(pathAreTrabalho);

			Runtime runtime = Runtime.getRuntime();

			Process processo;

			System.out.println("INICIO SCRIPT DE CONVERSÃO");
			
			String[] nargs = { "/bin/bash", "-c", scriptPython }; // Unix/Linux/MacOSX
			
			processo = runtime.exec(nargs, null, diretorio);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(processo.getInputStream()));

			String linha;
			
			
			
			while ((linha = bReader.readLine()) != null) {
				log += "\n" + linha;
				System.out.println(linha);
			}
			
			System.out.println("FIM SCRIPT DE CONVERSÃO");
			
			//FIM DO SCRIPT

			return log;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@SuppressWarnings("unused")
	private String ajustarArquivoFastaParaAnaliseOrtologia(String _pathArquivoFASTA, int _colunaID) {
		try {
			
			String log = "";
			
			// SCRIPT PARA AJUSTAR FASTA

			String scriptPython = "orthomclAdjustFasta" + " " + this.nomeArquivoAjustadoFASTA + " " + _pathArquivoFASTA + " " + _colunaID;
			
			File diretorio = new File(pathAreTrabalho);

			Runtime runtime = Runtime.getRuntime();

			Process processo;

			String[] nargs = { "/bin/bash", "-c", scriptPython }; // Unix/Linux/MacOSX
			
			System.out.println("INICIO SCRIPT DE AJUSTE");
			
			processo = runtime.exec(nargs, null, diretorio);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(processo.getInputStream()));

			String linha;
			
			while ((linha = bReader.readLine()) != null) {
				log += "\n" + linha;
				System.out.println(linha);
			}
			
			System.out.println("FIM SCRIPT DE AJUSTE");
			
			//FIM DO SCRIPT

			return log;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@SuppressWarnings("unused")
	private String executarAnaliseOrtologia(String _pathArquivoParaAnalise) {
		try {
			
			String log = "";
			
			// SCRIPT PARA ANALISAR ORTOLOGIA

			String scriptPerl = "cp -u " + _pathArquivoParaAnalise + ".fasta ../in/ ; ";
			//scriptPerl += "orthomcl-pipeline -i ../in/ -o ../out/ -m /home/thiago/Softwares/orthomcl-pipeline/scripts/orthomcl.conf --yes";
			File diretorio = new File(pathOrthoPipeline);

			Runtime runtime = Runtime.getRuntime();

			Process processo;

			String[] nargs = { "/bin/bash", "-c", scriptPerl }; // Unix/Linux/MacOSX
			
			System.out.println("INICIO SCRIPT DE ANALISE DE ORTOLOGIA");
			
			processo = runtime.exec(nargs, null, diretorio);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(processo.getInputStream()));

			String linha;
			
			while ((linha = bReader.readLine()) != null) {
				log += "\n" + linha;
				System.out.println(linha);
			}
			
			System.out.println("FIM SCRIPT");
			
			//FIM DO SCRIPT

			return log;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private String executarAnaliseFBA(String _nomeArquivoSBML, String _idRede) {

		try {
			
			String log = "";
		
			String scriptFBA = "python testeCCBH4851.py " + _nomeArquivoSBML + " " + _idRede;
			
			File diretorio = new File(pathAreTrabalho);

			Runtime runtime = Runtime.getRuntime();

			Process processo;

			String[] nargs = { "/bin/bash", "-c", scriptFBA }; // Unix/Linux/MacOSX
			
			System.out.println("INICIO ANALISE FBA");
			
			processo = runtime.exec(nargs, null, diretorio);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(processo.getInputStream()));

			String linha;
			
			while ((linha = bReader.readLine()) != null) {
				log += "\n" + linha;
				System.out.println(linha);
			}
			
			System.out.println("\nFIM SCRIPT");
			
			//FIM DO SCRIPT

			return log;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static HashMap<String, String> lerTabelaDePara(String _pathArquivoTabID) {
		try {
			
			HashMap<String, String> tabela = new HashMap<>();
			
			BufferedReader br = new BufferedReader(new FileReader(_pathArquivoTabID));
		  
			String linha;
			while ((linha = br.readLine()) != null) {
			     //System.out.println(linha);
			     String[] campos = linha.split("\t");
			     
			     String idPao = campos[7];
			     String idNCBI = campos[8];
			     
			     //System.out.println(idPao + " : " + idNCBI);
			     
			     tabela.put(idPao, idNCBI);
			}

			br.close();
			
			return tabela;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static HashMap<String, HashMap<String, String>> lerArquivoGBK(String _pathArquivoTabID) {
		try {
			
			HashMap<String, HashMap<String, String>> tabela = new HashMap<String, HashMap<String, String>>();
		       
			File protFile = new File(_pathArquivoTabID);
			
			FileInputStream is = new FileInputStream(protFile);
			
			LinkedHashMap<String, ProteinSequence> protSequences; //= GenbankReaderHelper.readGenbankProteinSequence(protFile);
		   
			GenbankReader<ProteinSequence, AminoAcidCompound> protReader = 
					new GenbankReader<ProteinSequence, AminoAcidCompound>(
							is,
							new GenericGenbankHeaderParser<ProteinSequence,AminoAcidCompound>(),
							new ProteinSequenceCreator(AminoAcidCompoundSet.getAminoAcidCompoundSet()
									)
							);
			   
			protSequences = protReader.process();
			
			for (String k : protSequences.keySet()) {
				
				ProteinSequence gbk = protSequences.get(k);
				
				//String source = gbk.getSource();
				
				//System.out.println(source);
				
				List<FeatureInterface<AbstractSequence<AminoAcidCompound>, AminoAcidCompound>> features =  gbk.getFeaturesByType("CDS");
			
				for (FeatureInterface<AbstractSequence<AminoAcidCompound>, AminoAcidCompound> f : features) {
					/*
					System.out.println("-----------------------------------------------------------");
					System.out.println(f.getShortDescription());
					System.out.println(f.getSource());
					*/
					Map<String, List<Qualifier>> qualifiers = f.getQualifiers();
					
					String id = null;
					HashMap<String, String> parametros = new HashMap<String, String>();
					for (String chave : qualifiers.keySet()) {
						
						List<Qualifier> qualis = qualifiers.get(chave);
						Qualifier q = qualis.get(0);
						
						//System.out.println(q.getName() + " : " + q.getValue());
						
						
						if (q.getName().equals("inference")) {
							String[] inf = q.getValue().split(":");
							
							id = inf[3];
						}						
						else if (q.getName().equals("protein_id")) {
							id = q.getValue();
						}
						else {
							parametros.put(q.getName(), q.getValue());
						}
					}
					
					tabela.put(id, parametros);
				}
			}
			
			is.close();
			
			return tabela;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void EnviarEmail(String _dataInicial, String _dataFinal) {	
		try {
			
			String titulo = "New Reconstruction started";
			String msg = "Reconstruction Data: \n"
				+ "\nID user: " + this.user.getId()
				+ "\nReconstruction user: " + this.user.getNome()
				+ "\nEmail: " + this.user.getEmail()
				+ "\nDate and Time Start: " + _dataInicial
				+ "\nDate and Time Finish: " + _dataFinal
				+ "\n\n\n\n"
				+ "-----------------------------------------------------"
				+ "\nSCRIPT OUTPUT"
				+ "====================================================="
				+ "\n" + this.logReconstrucao
				+ "";
			
			String pathAnexo = pathAreTrabalho + nomeArquivoSaidaSBML;
			EmailUtil.enviaEmail(titulo, msg, this.user.getEmail(), pathAnexo, nomeArquivoSaidaSBML);
			
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	//METODOS GETS E SETS ---------------- // --------------------------
	
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

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getNomeArquivoSBML() {
		return nomeArquivoSaidaSBML;
	}

	public void setNomeArquivoSBML(String nomeArquivoSBML) {
		this.nomeArquivoSaidaSBML = nomeArquivoSBML;
	}
	
	public String getNomeArquivoFASTA() {
		return nomeArquivoFASTA;
	}

	public void setNomeArquivoFASTA(String nomeArquivoFASTA) {
		this.nomeArquivoFASTA = nomeArquivoFASTA;
	}

	public String getNomeArquivoSaidaSBML() {
		return nomeArquivoSaidaSBML;
	}

	public void setNomeArquivoSaidaSBML(String nomeArquivoSaidaSBML) {
		this.nomeArquivoSaidaSBML = nomeArquivoSaidaSBML;
	}

	public String getNomeArquivoGBK() {
		return nomeArquivoGBK;
	}

	public void setNomeArquivoGBK(String nomeArquivoGBK) {
		this.nomeArquivoGBK = nomeArquivoGBK;
	}

	public String getNomeArquivoBaseSBML() {
		return nomeArquivoBaseSBML;
	}

	public void setNomeArquivoBaseSBML(String nomeArquivoBaseSBML) {
		this.nomeArquivoBaseSBML = nomeArquivoBaseSBML;
	}

	public String getNomeArquivoAjustadoFASTA() {
		return nomeArquivoAjustadoFASTA;
	}

	public void setNomeArquivoAjustadoFASTA(String nomeArquivoAjustadoFASTA) {
		this.nomeArquivoAjustadoFASTA = nomeArquivoAjustadoFASTA;
	}

	public String getNomeArquivoTabID() {
		return nomeArquivoTabID;
	}

	public void setNomeArquivoTabID(String nomeArquivoTabID) {
		this.nomeArquivoTabID = nomeArquivoTabID;
	}

	public Boolean getInferirGenes() {
		return inferirGenes;
	}

	public void setInferirGenes(Boolean inferirGenes) {
		this.inferirGenes = inferirGenes;
	}
	

	public String getLogReconstrucao() {
		return logReconstrucao;
	}

	public void setLogReconstrucao(String logReconstrucao) {
		this.logReconstrucao = logReconstrucao;
	}

	public String getIdReacaoBiomassa() {
		return idReacaoBiomassa;
	}

	public void setIdReacaoBiomassa(String idReacaoBiomassa) {
		this.idReacaoBiomassa = idReacaoBiomassa;
	}
	
	public List<String> getLitaIdsReactionsManuais() {
		return litaIdsReactionsManuais;
	}

	public void setLitaIdsReactionsManuais(List<String> litaIdsReactionsManuais) {
		this.litaIdsReactionsManuais = litaIdsReactionsManuais;
	}
	
	//FIM DOS METODOS GETS E SETS ---------------- // --------------------------
	
	@Override
	public void run() {
		reconstruirRedeMetabolicaV2L1();
	}
}
