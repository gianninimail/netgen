package testes;

public class ContadorDeLinhas {

	public static void main(String[] args) {
		
		Long lt = new Long(0);

		ContaLinhas cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/src/entidades");
		
		lt = lt + cl.linhas_total;
		
		cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/src/controle");
		
		lt = lt + cl.linhas_total;
		
		cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/src/dados");
		
		lt = lt + cl.linhas_total;
		
		cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/src/fasta");
		
		lt = lt + cl.linhas_total;
		
		cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/src/testes");
		
		lt = lt + cl.linhas_total;
		
		cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/src/util");
		
		lt = lt + cl.linhas_total;
		
		cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/WebContent/construcao");
		
		lt = lt + cl.linhas_total;
		
		cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/WebContent/includes");
		
		lt = lt + cl.linhas_total;
		
		cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/WebContent/templates");
		
		lt = lt + cl.linhas_total;
		
		cl = new ContaLinhas("/home/thiago/eclipse-workspace/netgen/WebContent/WEB-INF");
		
		lt = lt + cl.linhas_total;
		
		System.out.println(lt);
	}

}
