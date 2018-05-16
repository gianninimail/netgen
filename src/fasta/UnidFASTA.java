package fasta;

import java.io.Serializable;
import java.util.Set;

public final class UnidFASTA implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Set<CabecalhoFASTA> cabecalhos;
	private final String sequencia;
	private final String linhaCabecalho;
	private final int numeroLinhasCabecalho;

	public UnidFASTA( Set<CabecalhoFASTA> headers, String sequence, String headerLine ) {
		this.cabecalhos = headers;
		this.sequencia = sequence;
		this.linhaCabecalho = headerLine;
		this.numeroLinhasCabecalho = -1; // set default
	}
	
	public UnidFASTA( Set<CabecalhoFASTA> headers, String sequence, String headerLine, int headerLineNumber ) {
		this.cabecalhos = headers;
		this.sequencia = sequence;
		this.linhaCabecalho = headerLine;
		this.numeroLinhasCabecalho = headerLineNumber;
	}

	public Set<CabecalhoFASTA> getCabecalhos() {
		return cabecalhos;
	}
	
	public String getSequencia() {
		return sequencia;
	}

	public String getLinhaCabecalho() {
		return linhaCabecalho;
	}

	public int getNumeroLinhasCabecalho() {
		return numeroLinhasCabecalho;
	}


	public int hashCode() {
		return this.cabecalhos.hashCode() + this.sequencia.hashCode();
	}

	public boolean equals( Object o ) {
		if( !( o instanceof UnidFASTA ) ) return false;

		if( !((UnidFASTA)o).getSequencia().equals( this.getSequencia() ) )
			return false;

		if( !((UnidFASTA)o).getCabecalhos().equals( this.getCabecalhos() ) )
			return false;

		return true;
	}
}