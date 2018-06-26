package testes;

import java.util.ArrayList;
import java.util.List;

import controle.ReconstrucaoBean;
import entidades.Usuario;

public class TesteRecontrucao {

	public static void main(String[] args) {
		
		try {
			
			Usuario user = new Usuario();
			
			user.setId(new Long("1"));
			user.setLogin("thiago");
			user.setSenha("thiago");
			user.setNome("Thiago");
			user.setEmail("gianninimail@gmail.com");
			user.setTel("22-2222-2222");
			user.setTipoUsuario(1);
			
			//String nomeArquivoFASTA = "saidaFasta.fasta";
			String nomeArquivoSaidaSBML = "CCBH4851_v2.xml";
			String nomeArquivoGBK = "CCBH4851.gb";
			//String nomeArquivoBaseSBML = "MODEL1507180020.xml";
			String nomeArquivoBaseSBML = "iPAE1146.xml";
			//String nomeArquivoAjustadoFASTA = "fj";
			String nomeArquivoTabID = "tabelaNCBI.txt";
			
			List<String> listaReactionsManuais = new ArrayList<String>();
			
			
			//-----------GENES INFERIDOS MANUALMENTE---------//
			
			listaReactionsManuais.add("R_rxn02476"); //VERIFICAR - ESSENCIAL
			listaReactionsManuais.add("R_rxn00695"); //VERIFICAR - ESSENCIAL
			listaReactionsManuais.add("R_ATPM"); //VERIFICAR - ESSENCIAL
			
			
			//--------GENES NO GBK - EC NUMBERS --------
			
			
			listaReactionsManuais.add("R_rPY00182");//GBK
			listaReactionsManuais.add("R_rPY00180");//GBK
			listaReactionsManuais.add("R_rPY00166");//GBK
			listaReactionsManuais.add("R_rPY00171");//GBK
			listaReactionsManuais.add("R_rPY00174");//GBK
			listaReactionsManuais.add("R_rPY00177");//GBK
			listaReactionsManuais.add("R_rxn13789");//GBK
			listaReactionsManuais.add("R_rxn00420");//GBK
			listaReactionsManuais.add("R_rxn01268");//GBK
			listaReactionsManuais.add("R_rxn01729");//GBK
			listaReactionsManuais.add("R_rxn05064");//GBK
			
			//------------------------------------------
			
			listaReactionsManuais.add("R_rxn05468");//VERIFICAR TRANSPORTE ESSENCIAL
			
			/*
			listaReactionsManuais.add("R_rxn05467"); //TRANSPORTE
			listaReactionsManuais.add("R_rxn05319"); //TRANSPORTE
			listaReactionsManuais.add("R_rxn10577"); //TRANSPORTE
			*/
			
			//int colunaID = 1;
			
			String tituloRede = "Reconstrução completa";
			String versaoRede = "2";
			
			String textoSaida = "New Reconstruction started...."
					+ "\nAt the end, you will receive an email with all the information along with the SBML!"
					+ "\nSee you later!!!"
					+ "\n========================================================================================"
					+ "\n\n\n";
			
			// onde este numero é o intervalo de tempo ai voce controla este tempo aki
			//Thread thread = new Thread(new ReconstrucaoBean(tituloRede, versaoRede, user, nomeArquivoSBML, nomeArquivoGB, nomeArquivoBase, nomeArquivoFASTA, nomeArquivoAjustadoFASTA, nomeArquivoTabID));
			Thread thread = new Thread(new ReconstrucaoBean(tituloRede, versaoRede, user, 
					nomeArquivoSaidaSBML, 
					nomeArquivoGBK, 
					nomeArquivoBaseSBML,  
					nomeArquivoTabID,
					true,
					"R_PAO1_Biomass",
					listaReactionsManuais));
			
			thread.start();
			
			System.out.println(textoSaida);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
