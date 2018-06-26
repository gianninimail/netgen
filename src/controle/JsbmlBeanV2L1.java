package controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.ext.fbc.And;
import org.sbml.jsbml.ext.fbc.Association;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCReactionPlugin;
import org.sbml.jsbml.ext.fbc.FBCSpeciesPlugin;
import org.sbml.jsbml.ext.fbc.FluxObjective;
import org.sbml.jsbml.ext.fbc.GeneProduct;
import org.sbml.jsbml.ext.fbc.GeneProductAssociation;
import org.sbml.jsbml.ext.fbc.GeneProductRef;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.ext.fbc.Or;
import org.sbml.jsbml.ext.fbc.Objective.Type;
import org.sbml.jsbml.util.ModelBuilder;
import org.sbml.jsbml.util.Pair;
import org.sbml.jsbml.xml.XMLNode;

import dados.ComponenteDAO;
import dados.EnzimasHabilitaReacoesDAO;
import dados.EnzimeDAO;
import dados.GeneAssociacaoDAO;
import dados.GeneDAO;
import dados.GenomaDAO;
import dados.LimiteFluxoDAO;
import dados.ProteinaDAO;
import dados.ReacaoDAO;
import dados.ReacaoTemComponentesDAO;
import dados.ReconstrucaoDAO;
import dados.ReconstrucaoTemReacoesDAO;
import entidades.Componente;
import entidades.Enzima;
import entidades.EnzimasHabilitaReacoes;
import entidades.Gene;
import entidades.GeneAssociacao;
import entidades.Genoma;
import entidades.LimiteFluxo;
import entidades.Proteina;
import entidades.Reacao;
import entidades.ReacaoTemComponentes;
import entidades.Reconstrucao;
import entidades.ReconstrucaoTemReacoes;
import entidades.Usuario;
import util.Arvore;
import util.ArvoreUtil;
import util.PilhaUtil;

@ManagedBean
@ViewScoped
public class JsbmlBeanV2L1 implements Serializable {

	private static final long serialVersionUID = 1L;
	private static SBMLDocument sbml;
	
	public JsbmlBeanV2L1(String _path) {
		carregarSBML(_path);
	}
	
	private void carregarSBML(String _path) {
		try {

			SBMLReader leitorSBML = new SBMLReader();

			sbml = leitorSBML.readSBML(_path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SBMLDocument fileToSBML(String _path) {
		try {

			SBMLReader leitorSBML = new SBMLReader();

			SBMLDocument documentoSBML = leitorSBML.readSBML(_path);

			return documentoSBML;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public List<String> obterListaReactionDoModelo() {
		try {
			List<String> l = new ArrayList<String>();
			if (sbml != null) {
				ListOf<Reaction> lista = sbml.getModel().getListOfReactions();
				
				for (Reaction r : lista) {
					l.add(r.getId());
				}
			} 
			return l;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Reaction obterReactionPeloID(String _idReaction) {
		try {
			Reaction reaction;
			if (sbml != null) {
				reaction = sbml.getModel().getReaction(_idReaction);
			} else {
				return null;
			}
			return reaction;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Species obterSpeciesPeloID(String _idComponente) {
		try {
			Species componente;
			if (sbml != null) {
				componente = sbml.getModel().getSpecies(_idComponente);
			} else {
				return null;
			}
			return componente;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public List<Reaction> obterListaReactionPeloGenePAO1Comentarios(String _idGenePAO) {
		try {

			List<Reaction> reactions = new ArrayList<Reaction>();

			if (sbml != null) {

				Model modelo = sbml.getModel();

				List<Reaction> listaReaction = modelo.getListOfReactions();

				for (Reaction r : listaReaction) {
					
					List<String> listaGenes = obterListaGenesAssociadosDeComentarios(r);
					
					for (String gene : listaGenes) {
						
						if (_idGenePAO.equals(gene)) {
							
							reactions.add(r);
						}
					}
				}
			} 
			else {
				return null;
			}

			return reactions;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String obterSubsystemDeComentarios(Reaction _reaction) {
		try {
			
			//String t = "R_rxn03253";
			//Reaction r = _reaction = sbml.getModel().getReaction(t);
			//XMLNode noGenes = _reaction.getNotes().getChild(1).getChild(1).getChild(0);
			XMLNode noSubsystem = _reaction.getNotes().getChild(1).getChild(3).getChild(0);
			//XMLNode noEcNumber = r.getNotes().getChild(1).getChild(5).getChild(0);
			
			//String texto = strGenes.getCharacters().replaceAll("\\s", "").replaceAll("\\(|\\)", "");
			String[] info = noSubsystem.getCharacters().replaceAll(":\\s", ":").split(":");
			//String strGenes = noGenes.getCharacters();
			
			if (info.length > 1) {
				return info[1];
			}
			
			return "";
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String obterECsNumbersDeComentarios(Reaction _reaction) {
		try {
			
			XMLNode noECnumber = _reaction.getNotes().getChild(1).getChild(5).getChild(0);
			
			String[] info = noECnumber.getCharacters().replaceAll(":\\s", ":").split(":");
			
			if (info.length > 1) {
				return info[1];
			}
			
			return "";
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> obterListaGenesAssociadosDeComentarios(Reaction _reaction) {
		try {
			
			XMLNode noGenes = _reaction.getNotes().getChild(1).getChild(1).getChild(0);
			
			String[] genes = noGenes.getCharacters().replaceAll("\\s", "").replaceAll("\\(|\\)", "").split("or|:|and");
			
			List<String> listaGenes = new ArrayList<String>();
			
			for (int i = 1; i < genes.length; i++) {
				
				listaGenes.add(genes[i]);
			}
			
			return listaGenes;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String obterExpressaoAssociationGenesDeComentarios(Reaction _reaction) {
		try {
			
			//String t = "R_rxn03253";
			
			//Reaction r = _reaction = sbml.getModel().getReaction(t);
			XMLNode noGenes = _reaction.getNotes().getChild(1).getChild(1).getChild(0);
			//XMLNode noSubsystem = r.getNotes().getChild(1).getChild(3).getChild(0);
			//XMLNode noEcNumber = r.getNotes().getChild(1).getChild(5).getChild(0);
			
			//String texto = strGenes.getCharacters().replaceAll("\\s", "").replaceAll("\\(|\\)", "");
			String[] genes = noGenes.getCharacters().replaceAll(":\\s", ":").split(":");
			//String strGenes = noGenes.getCharacters();
			
			if (genes.length > 1) {
				return genes[1];
			}
			
			return "";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String[] obterFormulaAndChargeDeComentarios(Species _species) {
		try {
			
			//String t = "R_rxn03253";
			
			//Reaction r = _reaction = sbml.getModel().getReaction(t);
			XMLNode noFormula = _species.getNotes().getChild(1).getChild(1).getChild(0);
			XMLNode noCharge = _species.getNotes().getChild(1).getChild(3).getChild(0);
			//XMLNode noEcNumber = r.getNotes().getChild(1).getChild(5).getChild(0);
			
			String[] info = new String[2];
			
			info[0] = noFormula.getCharacters();
			info[1] = noCharge.getCharacters();
			
			return info;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Reacao inserirObjetosNaBase (Reaction _reaction, Reconstrucao _reconstrucao) {
		try {
			
			ReacaoDAO daoR = new ReacaoDAO();
			ComponenteDAO daoC = new ComponenteDAO();
			ReacaoTemComponentesDAO daoRTC = new ReacaoTemComponentesDAO();
			GeneAssociacaoDAO daoGA = new GeneAssociacaoDAO();
			LimiteFluxoDAO daoLF = new LimiteFluxoDAO();
			ReconstrucaoTemReacoesDAO daoRHR = new ReconstrucaoTemReacoesDAO();
			
			Reacao reacao = new Reacao();
			
			////INSERE REACAO NO BD
			if(!daoR.ExisteReacao(_reaction.getId())) {
				
				reacao = obterReacaoDeReaction(_reaction);
				daoR.Inserir(reacao);
			}
			else {
				
				reacao = daoR.PegarPeloID(_reaction.getId());
			}
			
			/*
			//TENTAR PEGAR EC NUNBER e CRIAR OBJETO ENZIMA NO BD
			String ECnumber = obterECsNumbersDeComentarios(_reaction);
			
			if (!ECnumber.equals("")) {
				List<Enzime> enzimes = obterEnzimesDoKEEG(ECnumber);
				
				
			}
			*/
			
			//MONTA LISTA DE GENES ASSOCIADOS E INSERE NO BD
			List<String> genesAssociados = obterListaGenesAssociadosDeComentarios(_reaction);
			
			for (String idGA : genesAssociados) {
				
				if (!daoGA.ExisteGeneAssociacao(idGA)) {
					
					GeneAssociacao ga = new GeneAssociacao(idGA, reacao);
					
					daoGA.Inserir(ga);
				}
			}
			
			//MONTA LISTA DE REAGENTES E INSERE NO BD
			List<SpeciesReference> speciesReferences = _reaction.getListOfReactants();
			
			for (SpeciesReference sr : speciesReferences) {
				
				Componente componente = obterComponenteDeSpeciesReference(sr);
				
				if (!daoC.ExisteCompound(componente.getAbbreviation())) {
					daoC.Inserir(componente);
				}
				
				ReacaoTemComponentes rtc = obterReacaoTemComponentes(reacao, componente, 'r');
				
				if (rtc != null) {
					if (!daoRTC.ExisteReacaoTemComponentes(reacao.getAbbreviation(), componente.getAbbreviation(), 'r')) {
						
						daoRTC.Inserir(rtc);
					}
				}
			}
			
			//MONTA LISTA DE PRODUTOS E INSERE NO BD
			speciesReferences = _reaction.getListOfProducts();
			
			for (SpeciesReference sr : speciesReferences) {
				
				Componente componente = obterComponenteDeSpeciesReference(sr);
				
				if (!daoC.ExisteCompound(componente.getAbbreviation())) {
					daoC.Inserir(componente);
				}
				
				
				ReacaoTemComponentes rtc = obterReacaoTemComponentes(reacao, componente, 'p');
				
				if (rtc != null) {
					if (!daoRTC.ExisteReacaoTemComponentes(reacao.getAbbreviation(), componente.getAbbreviation(), 'p')) {
						
						daoRTC.Inserir(rtc);
					}
				}
			}
			
			//MONTA LISTA DE LIMITES DE FLUXO E INSERE NO BD
			/*
				<parameter id="LOWER_BOUND" value="-10" units="mmol_per_gDW_per_hr" constant="false"/>
            	<parameter id="UPPER_BOUND" value="1000" units="mmol_per_gDW_per_hr" constant="false"/>
			 */
			KineticLaw ktc = _reaction.getKineticLaw();
			LocalParameter lb = ktc.getLocalParameter("LOWER_BOUND");
			LocalParameter ub = ktc.getLocalParameter("UPPER_BOUND");
			
			LimiteFluxo lfInferior = null;
			LimiteFluxo lfSuperior = null;
			
			//VERIFICANDO SE EXISTE LIMITE INFERIOR NO BD
			int val_inf = ((Double)lb.getValue()).intValue();
			String idLinf = "fb_" + val_inf;
			if (idLinf.contains("-")) {
				idLinf = idLinf.replace("-", "");
				idLinf += "_neg";
			}
			if (!daoLF.ExisteLimiteFluxo(idLinf)) {
				
				lfInferior = new LimiteFluxo();
				
				lfInferior.setId(idLinf);
				lfInferior.setValor(lb.getValue());
				lfInferior.setContante(true);
				
				daoLF.Inserir(lfInferior);
			}
			
			//VERIFICANDO SE EXISTE LIMITE SUPERIOR NO BD
			int val_sup = ((Double)ub.getValue()).intValue();
			String idLsup = "fb_" + val_sup;
			if (idLsup.contains("-")) {
				idLsup = idLinf.replace("-", "");
				idLsup += "_neg";
			}
			if (!daoLF.ExisteLimiteFluxo(idLsup)) {
				
				lfSuperior = new LimiteFluxo();
				
				lfSuperior.setId(idLsup);
				lfSuperior.setValor(ub.getValue());
				lfSuperior.setContante(true);
				
				daoLF.Inserir(lfSuperior);
			}
			
			ReconstrucaoTemReacoes rhrs = daoRHR.PegarPeloID(_reconstrucao.getId(), reacao.getAbbreviation());
			
			if (rhrs == null) {
				
				rhrs = new ReconstrucaoTemReacoes();
				
				lfInferior = daoLF.PegarPeloID(idLinf);
				lfSuperior = daoLF.PegarPeloID(idLsup);
				
				rhrs.setReconstrucao(_reconstrucao);
				rhrs.setReacao(reacao);
				rhrs.setLimiteInferior(lfInferior);
				rhrs.setLimiteSuperior(lfSuperior);
				
				daoRHR.Inserir(rhrs);
				
				_reconstrucao.getRhrs().add(rhrs);
			}
			
			//System.out.println(reacao);
			
			return reacao;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Enzima> obterEnzimesDoKEEG(String _ec_number) {	
		try {
			List<Enzima> enzimes = new ArrayList<Enzima>();
			
			String[] ecs = _ec_number.split("(,|;)");
			
			for (String ec : ecs) {
				
				ec = ec.replace("(", "");
				ec = ec.replace(")", "");
				
				EnzimeDAO dao = new EnzimeDAO();
				
				if (!dao.ExisteEnzime(ec)) {
					
					HtmlParserBean hpb = new HtmlParserBean();
					
					Enzima ez = hpb.obterEnzimePeloEcNumberKEGG(ec);
					
					enzimes.add(ez);
				}
				else {
					Enzima ez = dao.PegarPeloID(ec);
					
					enzimes.add(ez);
				}
			}
			
			return enzimes;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Componente obterComponenteDeSpeciesReference(SpeciesReference _speciesReference) {
		try {

			Species esp = _speciesReference.getSpeciesInstance();

			//FBCSpeciesPlugin fsp = (FBCSpeciesPlugin) esp.getPlugin(FBCConstants.shortLabel);

			String abbreviationC = esp.getId();
			//String compartment = esp.getCompartment();
			String name = esp.getName();
			
			String[] FormulaAndCharge = obterFormulaAndChargeDeComentarios(esp);
			
			String formula = FormulaAndCharge[0].substring(9);
			int charge = 0;
			
			if (!FormulaAndCharge[1].substring(8).equals("NaN")) {
				 Integer.valueOf(FormulaAndCharge[1].substring(8));
			}
			
			//String stoichiometry = Double.toString(_species.getStoichiometry());
			
			Componente comp = new Componente();
			
			comp.setAbbreviation(abbreviationC);
			comp.setName(name);
			comp.setFormula(formula);
			comp.setCharge(charge);
			
			return comp;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public ReacaoTemComponentes obterReacaoTemComponentes(Reacao _reac, Componente _comp, char _tipo_P_ou_R) {
		try {

			ReacaoTemComponentes rtc = new ReacaoTemComponentes();
			
			Reaction rea = obterReactionPeloID(_reac.getAbbreviation());

			if (_tipo_P_ou_R == 'p') {
			
				SpeciesReference espref = rea.getProductForSpecies(_comp.getAbbreviation());
				
				if (espref != null) {
					/*
					RTCid pk = new RTCid();
					
					pk.setReacao_id(_reac.getAbbreviation());
					pk.setComponente_id(_comp.getAbbreviation());
					pk.setTipo_R_ou_P('p');
	
					rtc.setId(pk);
					*/
					
					rtc.setReacao(_reac);
					rtc.setComponente(_comp);
					rtc.setTipo_P_ou_R('p');
					
					rtc.setEstequiometria(espref.getStoichiometry());
					rtc.setCompartimento(espref.getSpeciesInstance().getCompartment().charAt(0));
					
					return rtc;
				}
			}

			if (_tipo_P_ou_R == 'r') {
			
				SpeciesReference espref = rea.getReactantForSpecies(_comp.getAbbreviation());
			
				if (espref != null) {
					/*
					RTCid pk = new RTCid();
					
					pk.setReacao_id(_reac.getAbbreviation());
					pk.setComponente_id(_comp.getAbbreviation());
					pk.setTipo_R_ou_P('p');
	
					rtc.setId(pk);
					*/
				
					rtc.setReacao(_reac);
					rtc.setComponente(_comp);
					rtc.setTipo_P_ou_R('r');
					
					rtc.setEstequiometria(espref.getStoichiometry());
					rtc.setCompartimento(espref.getSpeciesInstance().getCompartment().charAt(0));
				
					return rtc;
				}
			}
				
			return null;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Reacao obterReacaoDeReaction(Reaction _reaction) {
		try {

			String[] res = obterEquacaoDefinicaoDeReaction(_reaction);
			
			Reacao reacao = new Reacao();
			
			reacao.setAbbreviation(_reaction.getId());
			reacao.setName(_reaction.getName());
			reacao.setDefinition(res[0]);
			reacao.setEquation(res[1]);
			reacao.setReverse(_reaction.getReversible());
			
			String exp = obterExpressaoAssociationGenesDeComentarios(_reaction);
			
			reacao.setAssociacao(exp);
			
			return reacao;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String[] obterEquacaoDefinicaoDeReaction(Reaction _reaction) {
		try {

			String[] resultado = new String[2];
			
			String def = ""; 
			String eq = "";

			//System.out.println("--------------------- LISTA REAGENTES --------------------------------");
			
			List<SpeciesReference> reagentes = _reaction.getListOfReactants();

			for (SpeciesReference sr : reagentes) {

				Species esp = sr.getSpeciesInstance();

				FBCSpeciesPlugin fsp = (FBCSpeciesPlugin) esp.getPlugin(FBCConstants.shortLabel);

				String abbreviationC = esp.getId();
				String formula = fsp.getChemicalFormula();
				String stoichiometry = Double.toString(sr.getStoichiometry());
				
				if (!eq.equals("")) {
					eq += " + ";
					def += " + ";
				}
				
				def += "(" + stoichiometry + ")" + abbreviationC;
				eq +=  "(" + stoichiometry + ")" + formula;
			}
			
			if (_reaction.getReversible()) {
				def += " <=> ";
				eq += " <=> ";
			}
			else {
				def += " => ";
				eq += " => ";
			}
			
			//System.out.println("--------------------------------------------------------");
			
			//System.out.println("--------------------- LISTA PRODUTOS --------------------------------");
			List<SpeciesReference> products = _reaction.getListOfProducts();
			for (SpeciesReference sr : products) {

				Species esp = sr.getSpeciesInstance();

				FBCSpeciesPlugin fsp = (FBCSpeciesPlugin) esp.getPlugin(FBCConstants.shortLabel);

				String abbreviation = esp.getId();
				String formula = fsp.getChemicalFormula();
				String stoichiometry = Double.toString(sr.getStoichiometry());
				
				if (!eq.endsWith("<=> ") || !eq.endsWith("=> ")) {
					eq += " + ";
					def += " + ";
				}
				
				def += "(" + stoichiometry + ")" + abbreviation;
				eq +=  "(" + stoichiometry + ")" + formula;
			}
			
			//System.out.println("--------------------------------------------------------");
			
			//System.out.println("DEFINIÇÃO: " + def);
			
			//System.out.println("FORMULA: " + eq);
			
			resultado[0] = def;
			resultado[1] = eq;
			
			return resultado;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Reconstrucao novaReconstrucaoPeloSbmlV2L1(String _titulo, String _versao, Map<String, HashMap<String, String>> _sequenciasGBK, Map<String, String> _tabNCBI, List<String> _genesNAOachados, Boolean _inferirGenes, String _idReacaoBiomassa, List<String> _litaIdsReactionsManuais, Usuario _user) {
		try {
			
			//SETANDO GENOMA DA RECONSTRUCAO
			GenomaDAO daoGenoma = new GenomaDAO();
			/*
			Genoma genoma = new Genoma();
			genoma.setId(id);
			genoma.setDefinition(definition);
			genoma.setBp(bp);
			
			daoGenoma.Inserir(genoma);
			*/
			
			Genoma genoma = daoGenoma.PegarPeloID("CP021380");
			Set<Genoma> genomas = new HashSet<Genoma>();
			genomas.add(genoma);
			
			//CRIAÇAO DE UMA NOVA RECONSTRUÇÃO NO BD
			ReconstrucaoDAO daoRec = new ReconstrucaoDAO();	
			
			Reconstrucao rec = new Reconstrucao();
			rec.setTitulo(_titulo);
			rec.setVersion(_versao);
			rec.setData(new Date());
			rec.setUser(_user);
			rec.setGenoma(genoma);
			
			daoRec.Inserir(rec);
			
			//----------------------------------
			
			Set<Reacao> reacoesDaReconstrucao = new HashSet<Reacao>();
			
			//System.out.println(_tabNCBI.size());
			
			for (Reaction reaction : sbml.getModel().getListOfReactions()) {
				
				List<Enzima> enzimasParaInserirNaBase = new ArrayList<Enzima>();
				List<Gene> genesParaInserirNaBase = new ArrayList<Gene>();
				List<Proteina> proteinasParaInserirNaBase = new ArrayList<Proteina>();
				List<EnzimasHabilitaReacoes> EHRsParaInserirNaBase = new ArrayList<EnzimasHabilitaReacoes>();
				
				List<String> listaGenes = obterListaGenesAssociadosDeComentarios(reaction);
				
				String expressao = obterExpressaoAssociationGenesDeComentarios(reaction);
				
				for (String gene : listaGenes) {
					
					String idNCBI = _tabNCBI.get(gene);
					
					if (!_sequenciasGBK.containsKey(idNCBI)) {
						expressao = expressao.replace(gene, "false");
					}
					else {
						expressao = expressao.replace(gene, "true");
						
						//ASSOCIAÇÔES COM O GENOMA / GENE / PROTEINA / ENZIMA
						HashMap<String, String> valores = _sequenciasGBK.get(idNCBI);
						
						//MONTA LISTA DE GENES DA REAÇÃO PARA INSERIR NO BD
						Gene gn = new Gene();
						gn.setId(gene);
						gn.setTem_grp(true);
						gn.setName(valores.get("gene"));
						gn.setReference_id(valores.get("locus_tag"));
						gn.setGenomas(genomas);
						genesParaInserirNaBase.add(gn);
						
						//MONTA LISTA DE PROTEINAS DA REAÇÃO PARA INSERIR NO BD
						Proteina pt = new Proteina();
						pt.setId(idNCBI);
						pt.setNome(valores.get("product"));
						pt.setSeq_aa(valores.get("translation"));
						pt.setGene(gn);
						//pt.setEnzima(enzima);
						proteinasParaInserirNaBase.add(pt);
						
						//MONTA LISTA DE ENZIMAS DA REAÇÃO PARA INSERIR NO BD
						if (valores.containsKey("EC_number")) {
							String ec = valores.get("EC_number");
							List<Enzima> enzimas = obterEnzimesDoKEEG(ec);
							for (Enzima e : enzimas) {
								enzimasParaInserirNaBase.add(e);
								EHRsParaInserirNaBase.add(new EnzimasHabilitaReacoes(pt, e));
							}
						}
					}
				}
				
				Queue<String> fila = PilhaUtil.obterFilaPosFixa(expressao);
				
				Arvore arvore = ArvoreUtil.criaArvore(fila);
				
				boolean teste = ArvoreUtil.resolverExpBooleana(arvore);
				
				if (teste && !listaGenes.isEmpty()) {
										
					Reacao reacao = inserirObjetosNaBase(reaction, rec);
					
					//inserirDadosAnotacao(genesParaInserirNaBase, proteinasParaInserirNaBase, enzimasParaInserirNaBase);
					
					inserirDadosAnotacao(genesParaInserirNaBase, proteinasParaInserirNaBase, enzimasParaInserirNaBase, EHRsParaInserirNaBase, reacao);
					
					reacoesDaReconstrucao.add(reacao);
				}
			}
			
			if (_inferirGenes) {
				
				List<Reaction> reactions = sbml.getModel().getListOfReactions();
				List<Reaction> reactionCandidatas = new ArrayList<Reaction>();
				List<Reaction> reactionInferidas = new ArrayList<Reaction>();
				boolean achou = false;
				
				for (Reaction reaction : reactions) {
					achou = false;
					for (Reacao reacao : reacoesDaReconstrucao) {
						
						if (reaction.getId().equals(reacao.getAbbreviation())) {
							achou = true;
							break;
						}
					}
					if (!achou) {
						reactionCandidatas.add(reaction);
					}
				}
				
				for (Reaction reaction : reactionCandidatas) {
					
					//List<String> listaGenes = obterListaGenesAssociadosDeComentarios(reaction);
					String subsytem = obterSubsystemDeComentarios(reaction);
					
					if (subsytem.equalsIgnoreCase("Exchange")) {
						
						System.out.println("Reação inferida no modelo: " + reaction.getId());
						
						Reacao reacao = inserirObjetosNaBase(reaction, rec);
						
						reactionInferidas.add(reaction);
						
						reacoesDaReconstrucao.add(reacao);
					}
					
					for (String idReactionManual : _litaIdsReactionsManuais) {
						
						if (reaction.getId().equals(idReactionManual)) {
							
							System.out.println("Reação inferida manualmente no modelo: " + reaction.getId());
							
							Reacao reacao = inserirObjetosNaBase(reaction, rec);
							
							reactionInferidas.add(reaction);
							
							reacoesDaReconstrucao.add(reacao);
						}
					}
					
				}
			}
			
			
			Reaction reactionBiomassa = obterReactionPeloID(_idReacaoBiomassa);
			Reacao reacBiomassa = inserirObjetosNaBase(reactionBiomassa, rec);
			reacoesDaReconstrucao.add(reacBiomassa);
			
			//rec.setReacoes(reacoesDaReconstrucao);
			
			return rec;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Reconstrucao novaReconstrucaoPeloSbmlPao2017(String _titulo, String _versao, Map<String, HashMap<String, String>> sequenciasGBK, Map<String, String> _tabNCBI, List<String> _genesNAOachados, Boolean _inferirGenes, String _idReacaoBiomassa, List<String> _litaIdsReactionsManuais) {
		try {
			
			//CRIAÇAO DE UMA NOVA RECONSTRUÇÃO NO BD
			ReconstrucaoDAO daoRec = new ReconstrucaoDAO();	
			
			Reconstrucao rec = new Reconstrucao();
			rec.setTitulo(_titulo);
			rec.setVersion(_versao);
			rec.setData(new Date());
			
			daoRec.Inserir(rec);
			//----------------------------------
			
			Set<Reacao> reacoesDaReconstrucao = new HashSet<Reacao>();
			
			//System.out.println(_tabNCBI.size());
			
			for (String idNew : sequenciasGBK.keySet()) {
				
				String idPao = _tabNCBI.get(idNew);
				
				if (idPao == null) {
					_genesNAOachados.add(idNew);
				}
				else {
					
					List<Reaction> reactions = obterListaReactionPeloGenePAO1Comentarios(idPao);
					
					if (reactions.isEmpty()) {
						_genesNAOachados.add(idPao);
					}
					else {
						for (Reaction r : reactions) {
							
							Reacao reacao = inserirObjetosNaBase(r, rec);
							
							reacoesDaReconstrucao.add(reacao);
						}
					}
				}
			}
			
			
			if (_inferirGenes) {
				
				List<Reaction> reactions = sbml.getModel().getListOfReactions();
				List<Reaction> reactionCandidatas = new ArrayList<Reaction>();
				List<Reaction> reactionInferidas = new ArrayList<Reaction>();
				boolean achou = false;
				
				for (Reaction reaction : reactions) {
					achou = false;
					for (Reacao reacao : reacoesDaReconstrucao) {
						
						if (reaction.getId().equals(reacao.getAbbreviation())) {
							achou = true;
							break;
						}
					}
					if (!achou) {
						reactionCandidatas.add(reaction);
					}
				}
				
				for (Reaction reaction : reactionCandidatas) {
					
					//List<String> listaGenes = obterListaGenesAssociadosDeComentarios(reaction);
					String subsytem = obterSubsystemDeComentarios(reaction);
					
					if (subsytem.equalsIgnoreCase("Exchange")) {
						
						System.out.println("Reação inferida no modelo: " + reaction.getId());
						
						Reacao reacao = inserirObjetosNaBase(reaction, rec);
						
						reactionInferidas.add(reaction);
						
						reacoesDaReconstrucao.add(reacao);
					}
					
					for (String idReactionManual : _litaIdsReactionsManuais) {
						
						if (reaction.getId().equals(idReactionManual)) {
							
							System.out.println("Reação inferida manualmente no modelo: " + reaction.getId());
							
							Reacao reacao = inserirObjetosNaBase(reaction, rec);
							
							reactionInferidas.add(reaction);
							
							reacoesDaReconstrucao.add(reacao);
						}
					}
					
				}
			}
			
			
			Reaction reactionBiomassa = obterReactionPeloID(_idReacaoBiomassa);
			Reacao reacBiomassa = inserirObjetosNaBase(reactionBiomassa, rec);
			reacoesDaReconstrucao.add(reacBiomassa);
			
			//rec.setReacoes(reacoesDaReconstrucao);
			
			return rec;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public boolean gerarSBMLdeReconstrucao(Reconstrucao _reconstrucao, String _idReacaoBiomassa, String _pathSaida) {
		try {
			
			//ReconstrucaoDAO daoRec = new ReconstrucaoDAO();
			LimiteFluxoDAO daoLF = new LimiteFluxoDAO();
			Map<String, Parameter> mapaFluxos = new HashMap<String, Parameter>();
			
			//Reconstrucao reconstrucao = daoRec.PegarPeloID(_reconstrucao.getId());
			
			//INFORMAÇOES DA MONTAGEM DO SBML ----------------------------------------------------
			int level = 3, version = 1;
			
			//String idReconstrucao = "CCBH4851", nomeReconstrucao = "Primeira Recontrução", versaoReconstrucao = "V0";
			//SBMLDocument doc = new SBMLDocument(level, version);
			
			//Classe construtora de objetos do SBML
			ModelBuilder construtor = new ModelBuilder(level, version);

			//Criação do modelo, com id, nome e versão
		    Model modelo = construtor.buildModel("CCBH4851", _reconstrucao.getId().toString() + ":" + _reconstrucao.getTitulo() + " - " + _reconstrucao.getVersion());
		    
		    //Classe responsavél po acrescentar função de análise de balanço de fluxo
		    FBCModelPlugin pluginModelo = (FBCModelPlugin) modelo.getPlugin(FBCConstants.shortLabel);
		    pluginModelo.setStrict(true);

		    //Criação das unidades de medidas para o modelo
		    /*
			    <unit scale="-3" exponent="1" multiplier="1" kind="mole"/>
	          	<unit scale="0" exponent="-1" multiplier="1" kind="gram"/>
	          	<unit scale="0" exponent="-1" multiplier="3600" kind="second"/>
	          	construtor.buildUnit(ud, multiplier, scale, kind, exponent);
		    */
		    UnitDefinition ud = construtor.buildUnitDefinition("mmol_per_gDW_per_hr", "mmol_per_gDW_per_hr");
		    construtor.buildUnit(ud, 1d, -3, Unit.Kind.MOLE, 1d);
		    construtor.buildUnit(ud, 1d, 0, Unit.Kind.GRAM, -1d);
		    construtor.buildUnit(ud, 3600d, 0, Unit.Kind.SECOND, -1d);
		    
		    //Criação de parâmetros de fluxo mínimo e máximo
		    /*
		     	<parameter id="flux_b1" constant="true"  value="1000" units="mmol_per_gDW_per_hr"/>
				<parameter id="flux_b4" constant="true"  value="-10" units="mmol_per_gDW_per_hr"/>
				<parameter id="flux_b5" constant="true"  value="-20" units="mmol_per_gDW_per_hr"/>
				<parameter id="flux_b6" constant="true"  value="8.39" units="mmol_per_gDW_per_hr"/>
				<parameter id="flux_b3" constant="true"  value="-1000" units="mmol_per_gDW_per_hr"/>
				<parameter id="flux_b2" constant="true"  value="0" units="mmol_per_gDW_per_hr"/>
		     
		    Parameter flux_b1 = construtor.buildParameter("flux_b1", "flux_b1", 1000d, true, modelo.getSubstanceUnits());
		    Parameter flux_b2 = construtor.buildParameter("flux_b2", "flux_b2", 0d, true, modelo.getSubstanceUnits());
		    Parameter flux_b3 = construtor.buildParameter("flux_b3", "flux_b3", -1000d, true, modelo.getSubstanceUnits());
		    Parameter flux_b4 = construtor.buildParameter("flux_b4", "flux_b4", -10d, true, modelo.getSubstanceUnits());
		    Parameter flux_b5 = construtor.buildParameter("flux_b5", "flux_b5", -20d, true, modelo.getSubstanceUnits());
		    Parameter flux_b6 = construtor.buildParameter("flux_b6", "flux_b6", 8.39d, true, modelo.getSubstanceUnits());
		    */
		    
		    for (LimiteFluxo lf : daoLF.listarTodos()) {
		    	Parameter param = construtor.buildParameter(lf.getId(), lf.getId(), lf.getValor(), lf.getContante(), ud.getId());
		    	mapaFluxos.put(lf.getId(), param);
			}
		    
		    //Criação de possíveis compartimento do modelo que está sendo gerado
		    /*
				<compartment id="c" name="Cytoplasm" spatialDimensions="3" constant="false"/>
				<compartment id="e" name="Extracellular" spatialDimensions="3" constant="false"/>
		     */
		    Compartment compartimentC = modelo.createCompartment("c");
		    compartimentC.setConstant(false);
		    compartimentC.setName("Cytoplasm");
		    compartimentC.setSpatialDimensions(3);
		    
		    //modelo.addCompartment(compartimentC);
		    		//construtor.buildCompartment("c", false, "Cytoplasm", 3d, 1d, modelo.getVolumeUnits());
		    
		    Compartment compartimentE = modelo.createCompartment("e");
		    compartimentE.setConstant(false);
		    compartimentE.setName("Extracellular");
		    compartimentE.setSpatialDimensions(3);
		    
		    //modelo.addCompartment(compartimentE);
		    		//construtor.buildCompartment("e", false, "Extracellular", 3d, 1d, modelo.getVolumeUnits());
		    
			//ReacaoDAO daoR = new ReacaoDAO(); 
			//ComponenteDAO daoC = new ComponenteDAO();
			//GeneAssociacaoDAO daoGA = new GeneAssociacaoDAO();
		    //////////////////////////////////////////////////////////////////////
		    List<GeneProduct> listaGeneProduct = new ArrayList<GeneProduct>();
		    
		    List<ReconstrucaoTemReacoes> rhrs = (List<ReconstrucaoTemReacoes>) _reconstrucao.getRhrs();
		    for (ReconstrucaoTemReacoes rhr : rhrs) {
				
		    	Reacao reacao = rhr.getReacao();
				
				List<ReacaoTemComponentes> rtcs = (List<ReacaoTemComponentes>) reacao.getRhcs();
				
				Reaction reaction = modelo.createReaction(reacao.getAbbreviation());
				reaction.setName(reacao.getName());
				reaction.setReversible(reacao.isReverse());
				
				modelo.addReaction(reaction);
				
				for (ReacaoTemComponentes rtc : rtcs) {
					
					Componente comp = rtc.getComponente();
					
					if (!modelo.containsSpecies(comp.getAbbreviation())) {
						
						String id = comp.getAbbreviation();
						String compartimento = id.substring(id.length() - 2, id.length());
						
						Species s;
						
						s = modelo.createSpecies(comp.getAbbreviation());
						s.setName(comp.getName());
						
						s.setHasOnlySubstanceUnits(false);
						s.setBoundaryCondition(false);
						s.setConstant(false);
						
						if (compartimento.equals("_c")) {
							
							s.setCompartment(compartimentC);
						}
						else {
							
							s.setCompartment(compartimentE);
						}
						
						//System.out.println(s);
					}
						
					Species s = modelo.getSpecies(comp.getAbbreviation());
					
					Pair<Double, Species> especie = new Pair<Double, Species>();
					especie.setKey(rtc.getEstequiometria());
					especie.setValue(s);
					
					if (rtc.getTipo_P_ou_R() == 'r') {
						
						construtor.buildReactants(reaction, especie);
					}
					else {
						
						construtor.buildProducts(reaction, especie);
					}	
				}
			
				//List<GeneAssociacao> genesAssociados = daoGA.obterGenesAssociacaoPorIdReacao(r.getAbbreviation());
				
				//PARAMETROS PARA USO DO FBA - MODULO ADICIONAL DO SBML - FBC
				FBCReactionPlugin reactionPlugin = (FBCReactionPlugin) reaction.getPlugin(FBCConstants.shortLabel);
				
				String associacao = reacao.getAssociacao();
				//System.out.println(reacao);
				
				if (!associacao.equals("")) {
					
					GeneProductAssociation gpa = reactionPlugin.createGeneProductAssociation();
					
					Queue<String> fila = PilhaUtil.obterFilaPosFixa(associacao);
					
					if (fila.size() > 1) {
					
						Arvore arvore = ArvoreUtil.criaArvore(fila);
						
						Association association = obterASSOCIATIONfromArvoreExpPosfixa(null, arvore, level, version, listaGeneProduct);
								
						gpa.setAssociation(association);
					}
					else {
						String idGene = fila.poll();
						
						GeneProductRef gpr = new GeneProductRef(level, version);
						gpr.setGeneProduct(idGene);
						
						gpa.setAssociation(gpr);
						
						GeneProduct gp = new GeneProduct(level, version);
						gp.setId(idGene);
						gp.setName(idGene);
						gp.setLabel(idGene);
						
						if (!listaGeneProduct.contains(gp)) {
							listaGeneProduct.add(gp);
						}		
					}
				}
				
				reactionPlugin.setLowerFluxBound(rhr.getLimiteInferior().getId());
			    reactionPlugin.setUpperFluxBound(rhr.getLimiteSuperior().getId());
			}
		    
		    for (GeneProduct gp : listaGeneProduct) {
		    	pluginModelo.getListOfGeneProducts().add(gp);
			}
			
			if (modelo.containsReaction(_idReacaoBiomassa)) {
				
				Objective objetivo = new Objective();
				
				objetivo.setId("obj");
				objetivo.setType(Type.MAXIMIZE);
				
				FluxObjective fo = new FluxObjective();
				fo.setId("um");
				fo.setReaction(_idReacaoBiomassa);
				fo.setCoefficient(1);
				
				objetivo.addFluxObjective(fo);
				
				pluginModelo.addObjective(objetivo);
				
				pluginModelo.setActiveObjective(objetivo);
			}
			
			SBMLDocument doc = construtor.getSBMLDocument();
		    doc.addDeclaredNamespace("html", JSBML.URI_XHTML_DEFINITION);
		    
		    new SBMLWriter().writeSBMLToFile(doc, _pathSaida);
		    
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Association obterASSOCIATIONfromArvoreExpPosfixa(Association _ass, Arvore _arvore, int _level, int _version, List<GeneProduct> _listaGeneProduct) {
		try {
			
			if (_arvore != null) {
				
				if(_arvore.getDescricao().equals("or")) {
					
					Association or = new Or(_level, _version);
					
					or = obterASSOCIATIONfromArvoreExpPosfixa(or, _arvore.getEsquerda(), _level, _version, _listaGeneProduct);
					
					or = obterASSOCIATIONfromArvoreExpPosfixa(or, _arvore.getDireita(), _level, _version, _listaGeneProduct);
					
					//A PRIMEIRA CHAMADA A ASSOCIACAO SEMPRE SERA NULA
					if (_ass != null) {
						if (_ass instanceof Or) {
							
							((Or)_ass).addAssociation(or);
						}
						else {
							
							((And)_ass).addAssociation(or);
						}
					}
					else {
						_ass = or;
					}
					
				}
				else if(_arvore.getDescricao().equals("and")) {
					
					Association and = new And(_level, _version);
					
					and = obterASSOCIATIONfromArvoreExpPosfixa(and, _arvore.getEsquerda(), _level, _version, _listaGeneProduct);
					
					and = obterASSOCIATIONfromArvoreExpPosfixa(and, _arvore.getDireita(), _level, _version, _listaGeneProduct);
					
					//A PRIMEIRA CHAMADA A ASSOCIACAO SEMPRE SERA NULA
					if (_ass != null) {
						if (_ass instanceof And) {
							
							((And)_ass).addAssociation(and);
						}
						else {
							
							((Or)_ass).addAssociation(and);
						}
					}
					else {
						_ass = and;
					}
				}
				else if (!_arvore.getDescricao().equals("or") && !_arvore.getDescricao().equals("and")) {
					
					//PARA INSERIR EM UMA LISTA PRA COLOCAR NO SBML
					GeneProduct gp = new GeneProduct(_level, _version);
					gp.setId(_arvore.getDescricao());
					gp.setName(_arvore.getDescricao());
					gp.setLabel(_arvore.getDescricao());

					if (!_listaGeneProduct.contains(gp)) {
						_listaGeneProduct.add(gp);
					}
					
					//ASSOCIACAO QUE ENTRA NA REALAÇÃO
					GeneProductRef gpr = new GeneProductRef(_level, _version);
					gpr.setGeneProduct(_arvore.getDescricao());
					
					if (_ass instanceof And) {
						
						((And)_ass).addAssociation(gpr);
					}
					else {
						
						((Or)_ass).addAssociation(gpr);
					}
				}
				
			}
			
			return _ass;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean inserirDadosAnotacao(List<Gene> _genes, List<Proteina> _proteinas, List<Enzima> _enzimes, List<EnzimasHabilitaReacoes> eHRsParaInserirNaBase, Reacao _reacao) {
		try {
			
			GeneDAO geneDAO = new GeneDAO();
			ProteinaDAO daoProteina = new ProteinaDAO();
			EnzimeDAO enzimaDAO = new EnzimeDAO();
			EnzimasHabilitaReacoesDAO daoEHR = new EnzimasHabilitaReacoesDAO();
			
			for (Gene gene : _genes) {
				if(!geneDAO.ExisteGene(gene.getId())) {
					geneDAO.Inserir(gene);
				}
			}
			
			for (Proteina proteina : _proteinas) {
				if(!daoProteina.ExisteProteina(proteina.getId())) {
					daoProteina.Inserir(proteina);
				}
			}
			
			for (Enzima enzime : _enzimes) {
				if(!enzimaDAO.ExisteEnzime(enzime.getEc_number())) {
					enzimaDAO.Inserir(enzime);
				}
			}
			
			for (EnzimasHabilitaReacoes EHR : eHRsParaInserirNaBase) {
				EHR.setReacao(_reacao);
				
				if (!daoEHR.ExisteEnzime(EHR)) {
					daoEHR.Inserir(EHR);
				}
			}
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
