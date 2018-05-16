package fasta;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a header associated with a sequence in the FASTA file
*/
public final class CabecalhoFASTA {

	public int hashCode() {
		return this.line.hashCode();
	}

	public boolean equals( Object o ) {
		if( !( o instanceof CabecalhoFASTA ) ) return false;

		return ((CabecalhoFASTA)o).getLine().equals( this.getLine() );
	}


	/**
	 * Constructor that parses a single header into it's parts
	 * @param line
	 * @throws Exception
	 */
	public CabecalhoFASTA( String line ) throws ExceptionFASTA {

		Pattern withoutDescription = Pattern.compile("^(\\S+)\\s*$");
		Pattern withDescription    = Pattern.compile("^(\\S+)\\s+(\\S+.*)$");

		Matcher m = withoutDescription.matcher( line );
		if( m.matches() ) {
			this.line = line;
			this.name = m.group( 1 );
			this.description = null;
		} else {
			m = withDescription.matcher( line );
			if( m.matches() ) {
				this.name = m.group( 1 );
				this.description = m.group( 2 );
				this.line = line;
			} else {
				throw new ExceptionFASTA( "Could not parse FASTA header line: \"" + line + "\"" );
			}
		}
	}
	

	/**
	 * Constructor that creates a new object from the provided parts
	 * 
	 * @param name
	 * @param description
	 * @param line
	 * @throws Exception
	 */
	public CabecalhoFASTA( String name, String description, String line ) {
		
		this.name = name;
		this.description = description;
		this.line = line;
	}

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getLine() {
		return line;
	}

	private final String name;
	private final String description;
	private final String line;
}