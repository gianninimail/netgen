package testes;

import java.util.List;

import controle.JsbmlBeanV2L1;

public class AnaliseRedes {

	public static void main(String[] args) {
		
		String pathAreTrabalho = "/ccbh4851system/";

		JsbmlBeanV2L1 sbmlThiago = new JsbmlBeanV2L1(pathAreTrabalho + "CCBH4851_v6.xml");
		
		List<String> lThiago = sbmlThiago.obterListaReactionDoModelo();
		
		///////////////////////////////////////////////////////////////////////////////////
		
		JsbmlBeanV2L1 sbmlArgolo = new JsbmlBeanV2L1(pathAreTrabalho + "ccbh.sbml");
		
		List<String> lArgolo = sbmlArgolo.obterListaReactionDoModelo();
		
		///////////////////////////////////////////////////////////////////////////////////
		
		int cont = 0;
		for (String i : lArgolo) {
			
			if (!lThiago.contains(i)) {
				//System.out.print(i + ":");
				System.out.println(sbmlThiago.obterReactionPeloID(i));
				cont++;
			}
		}
		
		System.out.println("Reações que não tem no SBML THIAGO " + cont);
		
		cont = 0;
		for (String i : lThiago) {
			
			if (!lArgolo.contains(i)) {
				System.out.println(i);
				cont++;
			}
		}
		
		System.out.println("Reações que não tem no SBML ARGOLO " + cont);

	}

}
