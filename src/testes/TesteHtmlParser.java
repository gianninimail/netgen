package testes;

import controle.HtmlParserBean;

public class TesteHtmlParser {

	public static void main(String[] args) {
		
		HtmlParserBean t = new HtmlParserBean();
		
		String urlParaVisitar = "http://www.genome.jp/dbget-bin/www_bget?pae:PA0106";
		//http://www.genome.jp/kegg-bin/get_htext?pae01000+PA4133 - Organismo e IdGene
		String ec_number = t.obterEcNumberDoIDKEGG(urlParaVisitar);
		/*
		String ec_teste = "1.3.3.3";
		String seq = "MAQQTMKALVKREAAKGIWMEEVPVPTPGPNQVLIKLEKTAICGTDLHIYLWDEWSQRTI"
				+ "KPGLTIGHEFVGRIAEIGPGVTGYEIGQRVSAEGHIVCGHCRNCRGGRPHLCPNTVGIGVNVN"
				+ "GAFAEYMVMPASNLWPIPDQIPSELAAFFDPYGNAAHCALEFDVIGEDVLITGAGPIGIIAAG"
				+ "ICKHIGARNVVVTDVNDFRLKLAADMGATRVVNVANQSLKDVMKELHMEGFDVGLEMSGNPRA"
				+ "FNDMLDCMYHGGKIAMLGIMPKGAGCDWDKIIFKGLTVQGIYGRKMYETWYKMTQLVLSGFPL"
				+ "GKVMTHQLPIDDFQKGFDLMEEGKAGKVVLSWN";
		
		String org = "pseudomonas+aeruginosa";
		
		String ec = t.buscarEcNumberBrenda(seq, org);
		
		System.out.println(ec);
		*/
		t.obterReacaoPeloEcNumberKEGG(ec_number);
		
		
		//t.buscarReactionBRENDA(ec_teste);

	}

}
