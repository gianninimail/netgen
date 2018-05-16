package fasta;

/**
 * Very simple example for processing a FASTA file.
 *
 */
public class Example {

	/**
	 * Iterate over the supplied FASTA filename and print simple information to
	 * console
     * 
	 * @param filename
	 * @throws Exception
	 */
	private void processFASTAFile( String filename ) throws Exception {

		// Instantiate a reader for the file with the supplied filename
		LeitorFASTA reader = LeitorFASTA.getInstance( filename );
		
		UnidFASTA entry = reader.readNext();
		while( entry != null ) {

			/*
			 *  A given FASTA entry may have multiple headers associated with a
			 *  sequence. Show them all.
			 */
			for( CabecalhoFASTA header : entry.getCabecalhos() ) {
				System.out.println( "Got a FASTA header with name=" +
					header.getName() + " and description=" + header.getDescription() );
			}
			
			// show the sequence for this FASTA entry
			System.out.println( "Got this sequence for those headers: " + entry.getSequencia() + "\n" );
			
			// get the next entry in the FASTA file
			entry = reader.readNext();
		}
		
	}
	

	/**
	 * Run program, supply full path to FASTA file as first argument
	 * @param args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {
		
		Example example = new Example();
		
		String path = "/home/thiago/projetos/ccbh4851-2018-01-26/ccbh4851_v4.fasta";
		
		example.processFASTAFile( path );
		
	}
	
}