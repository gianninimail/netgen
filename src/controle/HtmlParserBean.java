package controle;

import java.io.Serializable;
//import java.util.HashMap;
//import java.util.Map;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

//import org.jsoup.Connection;
//import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entidades.Reacao;
import entidades.Reconstrucao;
import fasta.UnidFASTA;

@ManagedBean
@ViewScoped
public class HtmlParserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String REF_ATRIBUTO_EC_NUMBER_KEGG = "www_bget?ec:";

	private static final String REF_ATRIBUTO_EC_NUMBER_BRENDA = "./enzyme.php?ecno=";

	private static final String URL_BASE_EC_NUMBER_KEGG = "http://www.genome.jp/dbget-bin/www_bget?ec:";

	private static final String URL_BASE_EC_NUMBER_BRENDA = "https://www.brenda-enzymes.org/enzyme.php?ecno=";

	//private static final String URL_BASE_SEQ_AA_BRENDA = "https://www.brenda-enzymes.org/sequences.php?";

	public String obterEcNumberDoIDKEGG(String _urlParaBuscar) {

		try {

			Document documento = Jsoup.connect(_urlParaBuscar).get();

			Elements links = documento.select("a[href]");

			for (Element a : links) {

				if (a.attr("href").contains(REF_ATRIBUTO_EC_NUMBER_KEGG)) {

					System.out.println("ACHOU!!! - EC: " + a.text());

					return a.text();
				}
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return null;
	}

	public Reacao obterReacaoPeloEcNumberKEGG(String _ecNumber) {

		try {

			Reacao reacao = new Reacao();
			
			Document documento = Jsoup.connect(URL_BASE_EC_NUMBER_KEGG + _ecNumber).get();

			String[] camposPesquisa = { "entry", "name", "sysname", "reaction", "substrate", "product" };

			Elements listaTHclassTH20 = documento.select("th.th20");
			Elements listaTHclassTH21 = documento.select("th.th21");

			Elements listaTotalTH = new Elements();

			listaTotalTH.addAll(listaTHclassTH20);
			listaTotalTH.addAll(listaTHclassTH21);

			for (Element celula : listaTotalTH) {

				String celulaTexto = celula.text();

				for (String campo : camposPesquisa) {

					if (celulaTexto.toLowerCase().contains(campo) && campo.equals("substrate")) {

						Element td = celula.lastElementSibling();

						String tdTexto = td.text();

						String[] substratos = tdTexto.split(";\\s");
						
						System.out.println(celulaTexto + ": " + tdTexto);
						
						for (String sub : substratos) {
							
							String[] componente = sub.split("\\[|\\]");
							
							System.out.println("Nome: " + componente[0]);
							System.out.println("ID KEGG: " + componente[1]);
						}

						break;
					}
					if (celulaTexto.toLowerCase().contains(campo) && campo.equals("product")) {

						Element td = celula.lastElementSibling();

						String tdTexto = td.text();
						
						String[] produtos = tdTexto.split(";");
						
						System.out.println(celulaTexto + ": " + tdTexto);
						
						for (String pro : produtos) {
							
							String[] componente = pro.split("\\[|\\]");
							
							System.out.println("Nome: " + componente[0]);
							System.out.println("ID KEGG: " + componente[1]);
						}

						break;
					}
				}
			}
			
			return reacao;
			
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	public void buscarReactionBRENDA(String _ecNumber) {

		try {

			Document documento = Jsoup.connect(URL_BASE_EC_NUMBER_BRENDA + _ecNumber).get();

			String celEC = "tab7r0sr0c0";
			String celName = "tab29r0sr0c0";
			String celReaction = "tab27r0c0";
			String celSysName = "tab40r0sr0c0";
			// String celSubstrate = "#tab29r0sr0c0";
			// String celProduct = "#tab29r0sr0c0";

			Element elEC = documento.getElementById(celEC);
			Element elName = documento.getElementById(celName);
			Element elSysname = documento.getElementById(celSysName);
			Element elReaction = documento.getElementById(celReaction);

			System.out.println(elEC.text());
			System.out.println(elName.text());
			System.out.println(elSysname.text());
			System.out.println(elReaction.text());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public String buscarEcNumberBrenda(String _seqAA, String _organism) {

		try {

			String urlTeste = "https://www.brenda-enzymes.org/sequences.php?sort="
					+ "&restricted_to_organism_group=&f%5BTaxTree_ID_min%5D=0&f%5BTaxTree_ID_max%5D=0"
					+ "&f%5Bstype_seq%5D=1&f%5Bseq%5D=" + _seqAA + "&f%5Blimit_range%5D=10"
					+ "&f%5Bstype_recom_name%5D=2&f%5Brecom_name%5D=&f%5Bstype_ec%5D=2"
					+ "&f%5Bec%5D=&f%5Bstype_accession_code%5D=2&f%5Baccession_code%5D="
					+ "&f%5Bstype_organism%5D=2&f%5Borganism%5D=" + _organism + "&f%5Bstype_no_of_aa%5D=1"
					+ "&f%5Bno_of_aa%5D=&f%5Bstype_molecular_weight%5D=1&f%5Bmolecular_weight%5D="
					+ "&f%5Bstype_no_tmhs%5D=1&f%5Bno_tmhs%5D=&Search=search#results";

			
			Document documento = Jsoup.connect(urlTeste).get();

			Elements links = documento.select("a[href]");

			for (Element a : links) {

				if (a.attr("href").contains(REF_ATRIBUTO_EC_NUMBER_BRENDA)) {

					System.out.println("ACHOU!!! - EC: " + a.text());

					return a.text();
				}
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return null;
	}

	public Reconstrucao continuarReconstrucaoPeloFasta(Reconstrucao _rec, Map<String, UnidFASTA> unidsFASTA, List<String> _genesNAOachados) {
		try {
			
			System.out.println(_genesNAOachados.size());
			
			for (String idGene : _genesNAOachados) {
				
				UnidFASTA unid = unidsFASTA.get(idGene);
				
				String[] cabecalho = unid.getLinhaCabecalho().split("\\|");
				
				String sequencia = unid.getSequencia();
				
				System.out.println(cabecalho[0] + " : " + cabecalho[1] + " : " + sequencia);
			}
			
			return _rec;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean existeReacaoPelaFormula(String _formulaReacao) {
		
		try {
			
			
			
			return true;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean existeReacaoPeloNome(String _nomeReacao) {
		
		try {
			
			return true;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
