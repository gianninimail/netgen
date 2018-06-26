package testes;

import controle.JsbmlBeanV2L1;
import dados.ReconstrucaoDAO;
import entidades.Reconstrucao;
import entidades.Usuario;

public class TesteJSBML {

	public static void main(String[] args) throws Exception {
		
		String pathAreTrabalho = "/ccbh4851system/";
		//final String arquivoSaidaSBML = "CCBH4851v1.xml";
		//String arquivoSBMLpao1 = "MODEL1507180020.xml";
		//String pathFasta = "/home/thiago/projetos/ccbh4851-2018-01-26/ccbh4851_v4.fasta";
		
		Usuario user = new Usuario();
		
		user.setId(new Long("1"));
		user.setLogin("thiago");
		user.setSenha("thiago");
		user.setNome("Thiago");
		user.setEmail("gianninimail@gmail.com");
		user.setTel("22-2222-2222");
		user.setTipoUsuario(1);
		
		//String nomeArquivoFASTA = "saidaFasta.fasta";
		String nomeArquivoSaidaSBML = "CCBH4851_v6.xml";
		//String nomeArquivoGBK = "CCBH4851.gb";//"seq.gb";
		//String nomeArquivoBase = "MODEL1507180020.xml";
		String nomeArquivoBaseSBML = "iPAE1146.xml";
		//String nomeArquivoAjustadoFASTA = "fj";
		//String nomeArquivoTabID = "ProteinTable187_165496.txt";

		//JsbmlBeanV2L1 j = new JsbmlBeanV2L1(pathAreTrabalho + nomeArquivoBaseSBML);
		
		//List<String> l = j.obterListaReactionDoModelo();
		////////////////////////////////////////////////////////////////////////////////////
		//String expressao = jbml.obterExpressaoAssociationGenesDeComentarios(null);
		
		//String expressao = "(((PA3891, or PA5376,) and (PA3890, or PA5377,) and (PA5378, or PA3889,)) or ((PA3891, or PA5376,) and (PA3890, or PA5377,) and (PA5378, or PA3889,) and PA3888,))";
		
		//String expressao = "(((PA3891 or PA5376) and (PA3890 or PA5377) and (PA5378 or PA3889)) or ((PA3891 or PA5376) and (PA3890 or PA5377) and (PA5378 or PA3889) and PA3888))";
		
		//String exp = PilhaUtil.obterPosFixa(expressao);
		
		//Queue<String> fila = PilhaUtil.obterFilaPosFixa(expressao);
		
		//Arvore arvore = ArvoreUtil.criaArvore(fila);
		
		//ArvoreUtil.percorrePos(arvore);
		
		//ArvoreUtil.percorrePre(arvore);
		
		//ArvoreUtil.percorreIn(arvore);
		
		//System.out.println("\n\n" + exp);
		//HtmlParserBean html = new HtmlParserBean();
		
		/*
		ReconstrucaoBean r = new ReconstrucaoBean("Teste", "v0", user, 
				nomeArquivoSaidaSBML, 
				nomeArquivoGBK, 
				nomeArquivoBaseSBML, 
				nomeArquivoFASTA, 
				nomeArquivoAjustadoFASTA, 
				nomeArquivoTabID);
				*/
		
		//Map<String, UnidFASTA> unidsFASTA = r.obterUnidadesFASTAdoArquivo(nomeArquivoFASTA);
		
		//List<String> genesNAOachados = new ArrayList<String>();
		
		//HashMap<String, String> tabela = ReconstrucaoBean.lerTabelaDePara(pathAreTrabalho + nomeArquivoTabID);
		
		//String id = tabela.get("NP_254187.1");
		
		//Map<String, HashMap<String, String>> sequenciasGBK = ReconstrucaoBean.lerArquivoGBK(pathAreTrabalho + nomeArquivoGBK);
		
		//HashMap<String, String> t = sequenciasGBK.get("NP_254187.1");
		//Map<String, String> tabNCBI = r.lerTabelaDePara(pathAreTrabalho + nomeArquivoTabID);;
		
		//Boolean inferirGenes = false;
		
		//Reconstrucao rec = jbml.novaReconstrucaoPeloSbmlPao2017("Teste", "v0", sequenciasGBK, tabNCBI, genesNAOachados, inferirGenes );
		
		//html.continuarReconstrucaoPeloFasta(rec, unidsFASTA, genesNAOachados);
		ReconstrucaoDAO dao = new ReconstrucaoDAO();
		
		Reconstrucao reconstrucao = dao.PegarPeloID(1L);
		
		JsbmlBeanV2L1 jbml = new JsbmlBeanV2L1(pathAreTrabalho + nomeArquivoBaseSBML);
		
		jbml.gerarSBMLdeReconstrucao(reconstrucao, "R_PAO1_Biomass", pathAreTrabalho + nomeArquivoSaidaSBML);
		
		System.out.println(" ---- FIM ----");
		
	}
	
	
}
