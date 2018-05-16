package controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;

import entidades.Usuario;
import util.EmailUtil;

public class ExecutarReconstrucao implements Runnable {

	private String sistema = "linux";

	private String textoSaidaAA2EC = "";
	private String textoSaidaEC2RXN = "";
	private String textoSaidaReactionList = "";
	private String textoSaidaFBA = "";

	private String path;
	private String textoSaida = "";
	private Usuario user;
	
	public ExecutarReconstrucao(Usuario _user, String _path) {
		this.user = _user;
		this.path = _path;
	}

	@Override
	public void run() {
		ExecutarEmSegundoPlano();
	}

	//METODOS GETS e SETS
	
	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getTextoSaidaAA2EC() {
		return textoSaidaAA2EC;
	}

	public void setTextoSaidaAA2EC(String textoSaidaAA2EC) {
		this.textoSaidaAA2EC = textoSaidaAA2EC;
	}

	public String getTextoSaidaEC2RXN() {
		return textoSaidaEC2RXN;
	}

	public void setTextoSaidaEC2RXN(String textoSaidaEC2RXN) {
		this.textoSaidaEC2RXN = textoSaidaEC2RXN;
	}

	public String getTextoSaidaReactionList() {
		return textoSaidaReactionList;
	}

	public void setTextoSaidaReactionList(String textoSaidaReactionList) {
		this.textoSaidaReactionList = textoSaidaReactionList;
	}

	public String getTextoSaidaFBA() {
		return textoSaidaFBA;
	}

	public void setTextoSaidaFBA(String textoSaidaFBA) {
		this.textoSaidaFBA = textoSaidaFBA;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

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
	
	//FIM DOS METODOS GETS e SETS
	
	public void ExecutarEmSegundoPlano() {

		List<String> listContent = new ArrayList<String>();

		try {

			// PRIMEIRO SCRIPT
			this.textoSaidaAA2EC = "";

			String pathname1 = "";
			String script1 = "";

			if (this.sistema.equals("linux")) {
				pathname1 = this.path;
				script1 = "perl aa2ec_ccbh4851.pl ./exact_match/ ./orthologues/ OrthoMCL_Result.txt ./cepas_PA/G1_CCBH4851.fasta ./cepas_PA/G2_19BR.fasta ./cepas_PA/G3_PAO1_NC_002516.faa ./cepas_PA/G4_GCF_000480375.1_Pseu_aeru_CF77_V1_protein.faa ./cepas_PA/G5_GCF_000481085.1_Pseu_aeru_BL03_V1_protein.faa ./cepas_PA/G6_GCF_000790355.1_AZPAE14872_protein.faa ./cepas_PA/G7_GCF_000794705.1_AZPAE14698_protein.faa newAnnotation.fasta tcdb_all.fasta";//request.getParameter("script"); // Unix/Linux/MacOSX
			} else {
				pathname1 = this.path;
				script1 = "orthologues_list.pl OrthoMCL_Result.txt ./cepas_PA/G1_CCBH4851.fasta ./exact_match/";// request.getParameter("script");
			}

			File dir1 = new File(pathname1);

			Runtime r1 = Runtime.getRuntime();

			Process p1;

			if (this.sistema.equals("linux")) {
				String[] nargs = { "/bin/bash", "-c", script1 }; // Unix/Linux/MacOSX
				p1 = r1.exec(nargs, null, dir1);
			} else {
				String[] nargs = { "cmd", "/c", script1 }; // Windows
				p1 = r1.exec(nargs, null, dir1);
			}

			BufferedReader is1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));

			String line1;
			
			System.out.println("INICIO SCRIPT 1");
			
			while ((line1 = is1.readLine()) != null) {
				listContent.add(line1);
				this.textoSaida += "\n" + line1;
				this.textoSaidaAA2EC += "\n" + line1;
				System.out.println(line1);
			}
			
			System.out.println("FIM SCRIPT 1");
			
			//FIM DO 1 SCRIPT

			// SEGUNDA SCRIPT
			this.textoSaidaEC2RXN = "";

			String pathname2 = "";
			String script2 = "";

			if (this.sistema.equals("linux")) {
				pathname2 = this.path;
				script2 = "perl ec2rxn.pl ec_ccbh4851.txt ./exact_match/";//request.getParameter("script"); // Unix/Linux/MacOSX
			} else {
				pathname2 = this.path;
				script2 = "orthologues_list.pl OrthoMCL_Result.txt ./cepas_PA/G1_CCBH4851.fasta ./exact_match/";// request.getParameter("script");
			}

			File dir2 = new File(pathname2);

			Runtime r2 = Runtime.getRuntime();

			Process p2;

			if (this.sistema.equals("linux")) {
				String[] nargs = { "/bin/bash", "-c", script2 }; // Unix/Linux/MacOSX
				p2 = r2.exec(nargs, null, dir2);
			} else {
				String[] nargs = { "cmd", "/c", script2 }; // Windows
				p2 = r2.exec(nargs, null, dir2);
			}

			BufferedReader is2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));

			String line2;

			System.out.println("INICIO SCRIPT 2");
			
			while ((line2 = is2.readLine()) != null) {
				listContent.add(line2);
				this.textoSaidaEC2RXN += "\n" + line2;
				this.textoSaida += "\n" + line2;
				System.out.println(line2);
			}
			
			System.out.println("FIM SCRIPT 2");
			
			//FIM DO 2 SCRIPT

			// TERCEIRO SCRIPT
			this.textoSaidaReactionList = "";

			String pathname3 = "";
			String script3 = "";

			if (this.sistema.equals("linux")) {
				pathname3 = this.path;
				script3 = "perl reactions_list_ec.pl ec_ccbh4851_not_in_PAO1.txt ./exact_match/";//request.getParameter("script"); // Unix/Linux/MacOSX
			} else {
				pathname3 = this.path;
				script3 = "orthologues_list.pl OrthoMCL_Result.txt ./cepas_PA/G1_CCBH4851.fasta ./exact_match/";// request.getParameter("script");
			}

			File dir3 = new File(pathname3);

			Runtime r3 = Runtime.getRuntime();

			Process p3;

			if (this.sistema.equals("linux")) {
				String[] nargs = { "/bin/bash", "-c", script3 }; // Unix/Linux/MacOSX
				p3 = r3.exec(nargs, null, dir3);
			} else {
				String[] nargs = { "cmd", "/c", script3 }; // Windows
				p3 = r3.exec(nargs, null, dir3);
			}

			BufferedReader is3 = new BufferedReader(new InputStreamReader(p3.getInputStream()));

			String line3 = "";

			System.out.println("INICIO SCRIPT 3");
			
			while ((line3 = is3.readLine()) != null) {
				listContent.add(line3);
				this.textoSaidaReactionList += "\n" + line3;
				this.textoSaida += "\n" + line3;
				System.out.println(line3);
			}
			
			System.out.println("FIM SCRIPT 3");
			
			//FIM DO 3 SCRIPT

			// QUARTO SCRIPT
			this.textoSaidaFBA = "";

			String pathname4 = "";
			String script4 = "";

			if (this.sistema.equals("linux")) {
				pathname4 = this.path;
				script4 = "perl create_ccbh4851_test2.pl imo1063_prot.csv imo1063_metabolites.csv pao1_ccbh_orthologues.txt";//request.getParameter("script"); // Unix/Linux/MacOSX
			} else {
				pathname4 = this.path;
				script4 = "orthologues_list.pl OrthoMCL_Result.txt ./cepas_PA/G1_CCBH4851.fasta ./exact_match/";// request.getParameter("script");
			}

			File dir4 = new File(pathname4);

			Runtime r4 = Runtime.getRuntime();

			Process p4;

			if (this.sistema.equals("linux")) {
				String[] nargs = { "/bin/bash", "-c", script4 }; // Unix/Linux/MacOSX
				p4 = r4.exec(nargs, null, dir4);
			} else {
				String[] nargs = { "cmd", "/c", script4 }; // Windows
				p4 = r4.exec(nargs, null, dir4);
			}

			BufferedReader is4 = new BufferedReader(new InputStreamReader(p4.getInputStream()));

			String line4;

			System.out.println("INICIO SCRIPT 4");
			
			while ((line4 = is4.readLine()) != null) {
				listContent.add(line4);
				this.textoSaidaFBA += "\n" + line4;
				this.textoSaida += "\n" + line4;
				System.out.println(line4);
			}
			
			System.out.println("FIM SCRIPT 4");

			//FIM DO 4 SCRIPT
			
			//ENVIAR E-MAIL
			
			EnviarEmail();
			
			//FIM ENVIAR E-MAIL
			
			// Cadastro da REQUISICAO no BD
			this.user.setDataCadastro(new Date());
			this.user.setTipoUsuario(1);

			// FabricaConexao fabrica = new FabricaConexao();
			// Connection conexao = fabrica.fazerConexao();
			//
			// UsuarioDAO dao = new UsuarioDAO(conexao);
			// dao.Inserir(this.usuario);
			//
			// this.listaUsuarios = dao.listarTodos();
			//
			// this.usuarios = new ListDataModel<Usuario>(listaUsuarios);
			//
			// fabrica.fecharConexao();

			//JSFUtil.adicionarMensagemSucesso("Usuario cadastrado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void EnviarEmail() {
		
		//util.EmailUtil email = new EmailUtil();
		
		try {
			
			String titulo = "New Reconstruction started";
			String msg = "Reconstruction Data: \n"
				+ "\nID user: " + this.user.getId()
				+ "\nReconstruction user: " + this.user.getNome()
				+ "\nEmail: " + this.user.getEmail()
				+ "\nDate and Time Start: " + new Date().toString()
				+ "\n\n\n\n"
				+ "================================================================="
				+ "\nSCRIPT OUTPUT"
				+ "\n" + this.textoSaida
				+ "";
			
			String pathAnexo = "/home/thiago/projetos/RECONSTRUCOES/ccbh4851-marcio/ccbh4851.sbml";
			EmailUtil.enviaEmail(titulo, msg, this.user.getEmail(), pathAnexo, "ccbh4851.sbml");
			
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

}
