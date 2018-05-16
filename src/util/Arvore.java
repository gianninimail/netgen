package util;

public class Arvore {

	private String descricao;
	private Arvore esquerda;
	private Arvore direita;
	private int numNo;

	public Arvore() {
		this.descricao = "";
		this.esquerda = null;
		this.direita = null;
	}

	public Arvore(String info) {
		this.descricao = info;
		this.esquerda = null;
		this.direita = null;
	}

	public Arvore(String _desc, int _numNo) {
		this.descricao = _desc;
		this.esquerda = null;
		this.direita = null;
		this.numNo = _numNo;
	}

	public Arvore(Arvore no) {
		this.descricao = no.descricao;
		this.esquerda = no.esquerda;
		this.direita = no.direita;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public Arvore getEsquerda() {
		return this.esquerda;
	}

	public Arvore getDireita() {
		return this.direita;
	}

	public void setDescricao(String _descricao) {
		this.descricao = _descricao;
	}

	public void setEsquerda(Arvore _esquerda) {
		this.esquerda = _esquerda;
	}

	public void setDireita(Arvore _direita) {
		this.direita = _direita;
	}

	public int getNumNo() {
		return numNo;
	}

	public void setNumNo(int numNo) {
		this.numNo = numNo;
	}
}
