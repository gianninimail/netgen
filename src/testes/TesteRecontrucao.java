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
			String nomeArquivoSaidaSBML = "CCBH4851_v1.xml";
			String nomeArquivoGBK = "CCBH4851.gb";
			//String nomeArquivoBaseSBML = "MODEL1507180020.xml";
			String nomeArquivoBaseSBML = "iPAE1146.xml";
			//String nomeArquivoAjustadoFASTA = "fj";
			String nomeArquivoTabID = "tabelaNCBI.txt";
			
			List<String> listaReactionsManuais = new ArrayList<String>();
			
			
			//-----------GENES INFERIDOS MÁRCIO---------//
			listaReactionsManuais.add("R_rxn02476");
			listaReactionsManuais.add("R_rxn00695");
			listaReactionsManuais.add("R_ATPM");
			//------------------------------------------
			listaReactionsManuais.add("R_rxn05468");//VERIFICAR
			listaReactionsManuais.add("R_rPY00182");
			listaReactionsManuais.add("R_rPY00180");
			listaReactionsManuais.add("R_rPY00166");
			listaReactionsManuais.add("R_rPY00171");
			listaReactionsManuais.add("R_rPY00174");
			listaReactionsManuais.add("R_rPY00177");
			//--------GENES NO GBK - EC NUMBERS --------
			//listaReactionsManuais.add("R_rxn01268");
			//listaReactionsManuais.add("R_rxn01729");
			//listaReactionsManuais.add("R_rxn05064");
			//listaReactionsManuais.add("R_rxn13789");
			//listaReactionsManuais.add("R_rxn00420");
			
			//listaReactionsManuais.add("R_rxn05467");
			//listaReactionsManuais.add("R_rxn05319");
			//listaReactionsManuais.add("R_rxn10577");
			
			
			
			
			//int colunaID = 1;
			
			String tituloRede = "Reconstrução completa";
			String versaoRede = "1";
			
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