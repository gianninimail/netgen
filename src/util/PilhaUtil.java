package util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PilhaUtil {

	static int Prioridade(String op) { // devolve a prioridade do operador
		int p = 0;

		switch (op) {
		case "(":
			p = 1;
			break;

		case "and":
			p = 2;
			break;

		case "or":
			p = 3;
		}

		return (p);
	}

	static boolean EOperador(String op) // verifica se o caractere é operador
	{
		if( op.equals("(") || op.equals(")") || op.equals("or") || op.equals("and") ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String obterPosFixa(String _expressao) {
		
		String posfixa = "";
		
		String eltopo;
		Stack<String> P = new Stack<String>();
		
		String exp = _expressao.replaceAll("\\(", "\\( ").replaceAll("\\)", "\\ )");
		
		String[] itens = exp.split("\\s");
		
		for (String item : itens) {

			String c = item;
			
			if (!EOperador(c)) { // operandos são impressos diretamente
				
				//System.out.print(c);
				posfixa += c;
			}
			else // é um operador
			if (P.isEmpty() || c.equals("(")) { // operador deve ser empilhado
				
				P.push(c);
			}
			else if (c.equals(")")) { // deve desempilhar e imprimir até achar '('
				
				eltopo = P.peek();
				
				while (!eltopo.equals("(")) {
					
					//System.out.print(eltopo);
					posfixa += eltopo;
					
					P.pop();
					eltopo = P.peek();
				}
				
				P.pop(); // desempilha o "("
			} 
			else {
				
				eltopo = P.peek();
				
				while (!P.isEmpty() && Prioridade(eltopo) >= Prioridade(c)) {
					
					//System.out.print(eltopo);
					posfixa += eltopo;
					
					P.pop(); // enquanto o topo da pilha tem prioridade
									// maior, desempilha e imprime
					if (!P.isEmpty())
						eltopo = P.peek();
				}
				
				P.push(c);
			}
		}

		while (!P.isEmpty()) { // últimos operadores que sobraram
			
			//System.out.print(P.peek());
			posfixa += P.peek();
			
			P.pop();
		}
		
		return posfixa;
	}
	
	public static Queue<String> obterFilaPosFixa(String _expressao) {
		
		Queue<String> fila = new LinkedList<String>();
		
		String eltopo;
		Stack<String> pilha = new Stack<String>();
		
		String exp = _expressao.replaceAll("\\(", "\\( ").replaceAll("\\)", "\\ )");
		
		String[] itens = exp.split("\\s");
		
		for (String item : itens) {

			String c = item;
			
			if (!EOperador(c)) { // operandos são impressos diretamente
				
				//System.out.print(c);
				fila.offer(c);
			}
			else // é um operador
			if (pilha.empty() || c.equals("(")) { // operador deve ser empilhado
				
				pilha.push(c);
			}
			else if (c.equals(")")) { // deve desempilhar e imprimir até achar '('
				
				eltopo = pilha.peek();
				
				while (!eltopo.equals("(")) {
					
					//System.out.print(eltopo);
					fila.offer(eltopo);
					
					pilha.pop();
					eltopo = pilha.peek();
				}
				
				pilha.pop(); // desempilha o "("
			} 
			else {
				
				eltopo = pilha.peek();
				
				while (!pilha.empty() && Prioridade(eltopo) >= Prioridade(c)) {
					
					//System.out.print(eltopo);
					fila.offer(eltopo);
					
					pilha.pop(); // enquanto o topo da pilha tem prioridade
									// maior, desempilha e imprime
					if (!pilha.empty())
						eltopo = pilha.peek();
				}
				
				pilha.push(c);
			}
		}

		while (!pilha.empty()) { // últimos operadores que sobraram
			
			//System.out.print(pilha.peek());
			fila.offer(pilha.peek());
			
			pilha.pop();
		}
		
		return fila;
	}
}
