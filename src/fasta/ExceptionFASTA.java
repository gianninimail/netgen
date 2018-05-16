package fasta;

/**
 * A data error has been found in the file being parsed.  
 * 
 * The standard Exception message holds the message about the data error.
 *
 */
public class ExceptionFASTA extends Exception {

	private static final long serialVersionUID = 1L;

	public ExceptionFASTA( String message ) {
		
		super(message);
	}
}
