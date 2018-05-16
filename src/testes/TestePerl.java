package testes;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestePerl {

	public static void main(String[] args) {
//		EntityManager unGerenciadora = JPAutil.getEntityManager();
//		EntityTransaction tx = unGerenciadora.getTransaction();
//		
//		tx.begin();
//		
//		Reaction reac = new Reaction(new Long(1), "Teste", "http://teste.com");
//		unGerenciadora.persist(reac);
//		
//		tx.commit();
//		
//		unGerenciadora.close();
//		JPAutil.close();
//		System.out.println("Teste Main Perl 123");
		List<String> listContent = new ArrayList<String>();
		
		try {
			String pathname = "E:/PesquisaBCS/RECONSTRUCOES/reconstruction-2016-07-28/";
			File dir = new File(pathname);
			String script = "orthologues_list.pl OrthoMCL_Result.txt ./cepas_PA/G1_CCBH4851.fasta ./exact_match/";//request.getParameter("script");
			
			Runtime r = Runtime.getRuntime();
			
			String[] nargs = { "cmd", "/c", script }; // Windows
			Process p = r.exec(nargs, null, dir);
			
			BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line;
			
			while ((line = is.readLine()) != null) {
			    listContent.add(line);
			    System.out.println(line);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		int tam = listContent.size();
		
		System.out.println(listContent.get(tam-1));
	}
}
