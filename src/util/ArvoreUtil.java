package util;

import java.util.Queue;
import java.util.Stack;

public class ArvoreUtil {

	static String str = "";
	
	// ESTE METODO CRIARA SUA ARVORE COM OS OPERADORES NO CAMPO INFO DE CADA ARVORE
	private static boolean ehOperador(String op) {
		
		if (op.equals("(") || op.equals(")") || op.equals("or") || op.equals("and")) {
			return true;
		} 
		else {
			return false;
		}
	}
	
	public static Arvore criaArvore(Queue<String> _filaPosFixa) {
		
		int numNo = 0;
		
		Stack<Arvore> pilha = new Stack<Arvore>();
		
		while (!_filaPosFixa.isEmpty()) {
			
			String item = _filaPosFixa.poll();
			Arvore arv = new Arvore(item, numNo);
			
			if (ehOperador(item)) {
				Arvore aux = pilha.pop();
				Arvore aux2 = pilha.pop();
				arv.setEsquerda(aux2);
				arv.setDireita(aux);
				pilha.push(arv);
			} 
			else {
				Arvore p = new Arvore(arv);
				pilha.push(p);
			}
			
			numNo++;
		}
		
		return pilha.peek();
	}
	
	public static void percorrePre(Arvore _arvBinaria) {
		if (_arvBinaria != null) {
			str = str + " " + _arvBinaria.getDescricao();
			percorrePre(_arvBinaria.getEsquerda());
			percorrePre(_arvBinaria.getDireita());
		}
		
		System.out.println(str);
	}

	public static void percorreIn(Arvore arvBin) {
		if (arvBin != null) {
			percorreIn(arvBin.getEsquerda());
			str = str + " " + arvBin.getDescricao();
			percorreIn(arvBin.getDireita());
		}
		
		System.out.println(str);
	}

	public static void percorrePos(Arvore arvBin) {
		
		if (arvBin != null) {
			percorrePos(arvBin.getEsquerda());
			percorrePos(arvBin.getDireita());
			str = str + " " + arvBin.getDescricao();
		}
		
		System.out.println(str);
	}
	
	public static boolean resolverExpBooleana(Arvore arvBin) {
		
		if (arvBin != null) {
			
			if (ehOperador(arvBin.getDescricao())) {
				
				boolean esq = resolverExpBooleana(arvBin.getEsquerda());
				boolean dir = resolverExpBooleana(arvBin.getDireita());
				
				if (arvBin.getDescricao().equals("or")) {
					return esq || dir;
				}
				else {
					return esq && dir;
				}
			}
			else {
				return Boolean.valueOf(arvBin.getDescricao());
			}
		}
		
		return true;
	}
	

/*
	public Arvore criaArvore(String suaExpressaoPosFixa) {
		
		Stack<Arvore> pilha = new Stack<Arvore>();
		StringTokenizer comando = new StringTokenizer(suaExpressaoPosFixa);
		
		while (comando.hasMoreTokens()) {
			
			String str = comando.nextToken();
			Arvore arv = new Arvore(str);
			
			if (ehOperador(str)) {
				Arvore aux = (Arvore) pilha.pop();
				Arvore aux2 = (Arvore) pilha.pop();
				arv.setEsq(aux2);
				arv.setDir(aux);
				pilha.push(arv);
			} 
			else {
				Arvore p = new Arvore(arv);
				pilha.push(p);
			}
			
			this.cont++;
		}
		
		return (Arvore) pilha.peek();
	}
*/
}
