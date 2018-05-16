package fasta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class LeitorFASTA {

	/**
	 * Get an instance of this class
	 * @param filename The filename of the FASTA file to read
	 * @return
	 * @throws Exception If there is a problem
	 */
	public static LeitorFASTA getInstance( String filename ) throws Exception {

		if (filename == null)
			throw new IllegalArgumentException( "filename may not be null" );

		return getInstance( new File( filename ) );
	}

	/**
	 * Get an instance of this class
	 * @param filename The filename of the FASTA file to read
	 * @return
	 * @throws Exception If there is a problem
	 */
	public static LeitorFASTA getInstance( File file ) throws Exception {


		if ( file == null ) {
			
			throw new IllegalArgumentException( "file may not be null" );
		}
		
		if ( ! file.exists() ) {
			
			throw new IllegalArgumentException( "File does not exist: " + file.getAbsolutePath() );
		}
		
		FileInputStream fileInputStream = new FileInputStream( file );
		
		return getInstance( fileInputStream );
	}


	/**
	 * Get an instance of this class
	 * @param is An InputStream for the FASTA data
	 * @return
	 * @throws Exception If there is a problem
	 */
	public static LeitorFASTA getInstance( InputStream inputStream ) throws Exception {

		if (inputStream == null)
			throw new IllegalArgumentException( "inputStream may not be null" );


		LeitorFASTA reader = new LeitorFASTA();

		InputStreamReader isr = new InputStreamReader( inputStream, ConstantesLeitorFASTA.inputCharSet );
		reader.br = new BufferedReader( isr );

		return reader;
	}

	/**
	 * Close the connection with the FASTA file
	 * @throws Exception
	 */
	public void close() throws Exception {
		if( this.br != null )
			this.br.close();

		this.br = null;
	}


	/**
	 * Get the next entry in the FASTA file. Returns null if end of file has been reached.
	 *
	 * @return
	 * @throws ExceptionFASTA for data errors
	 * @throws Exception
	 */
	public UnidFASTA readNext() throws ExceptionFASTA, Exception {

		/*
		 * It is assumed the last read correctly returned a Set of headers and a sequence
		 * So, it is therefor assumed the BufferedReader's next line read will be a header line
		 * followed by sequence lines (unless last read returned false (end of file) )
		 */

		String line = null;
		if( this.lineNumber == 0 )
			this.lastLineRead = this.br.readLine();

		line = this.lastLineRead;

		if (line == null) return null;			// we've reached the end of the file
		this.lineNumber++;

		int headerLineNumber = this.lineNumber;
		
		if (!line.startsWith( ">" ) )
			throw new ExceptionFASTA( "Line Number: " + this.lineNumber + " - Expected header line, but line did not start with \">\"." );

		String headerLine = line;

		// the headers for this entry
		Set<CabecalhoFASTA> headers = new HashSet<CabecalhoFASTA>();
		StringBuilder sequence = new StringBuilder();

		line = line.substring(1, line.length());	// strip off the leading ">" on the header line

		/*
		 * In FASTA files, multiple headers can be associated with the same sequence, and will
		 * be present on the same line.  The separate headers are separated by the CONTROL-A
		 * character, so we split on that here, and save each to the headers Set
		 */
		String[] lineHeaders = line.split("\\cA");
		for (int i = 0; i < lineHeaders.length; i++) headers.add( new CabecalhoFASTA( lineHeaders[i] ) );

		// The next line must be a sequence line
		line = this.br.readLine();
		this.lastLineRead = line;

		while (line.startsWith( ";" )) {
			this.lineNumber++;
			line = this.br.readLine();
			this.lastLineRead = line;
		}
		if (line == null || line.startsWith( ">" ))
			throw new ExceptionFASTA( "Did not get a sequence line after a header line (Line Number: " + this.lineNumber );


		// loop through the file, reading sequence lines until we hit the next header line (or the end of the file)
		while (line != null) {

			//If we've reached a new header line (marked with a leading ">"), then we're done.
			if (line.startsWith( ">" )) {
				break;
			}

			this.lineNumber++;

			// build the sequence, if it's not a comment line
			if (!line.startsWith( ";" )) {

				// upper-case the sequence line
				line = line.toUpperCase();

				sequence.append( line );
			}

			line  = this.br.readLine();
			this.lastLineRead = line;
		}
		
		String sequenceString = sequence.toString();
		
		sequenceString = sequenceString.trim();

		// If we've made it here, we've read another sequence entry in the FASTA data
		return new UnidFASTA( headers, sequenceString, headerLine, headerLineNumber );
	}

	private BufferedReader br;
	private int lineNumber;
	private String lastLineRead;
}
