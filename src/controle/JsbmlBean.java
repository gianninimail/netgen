package controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

//import static org.sbml.jsbml.util.ModelBuilder.buildUnit;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.JSBML;
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
import org.sbml.jsbml.ext.fbc.Objective.Type;
import org.sbml.jsbml.ext.fbc.Or;
import org.sbml.jsbml.util.ModelBuilder;
import org.sbml.jsbml.util.Pair;

import dados.ComponenteDAO;
import dados.GeneAssociacaoDAO;
import dados.OrthologDAO;
import dados.ReacaoDAO;
import dados.ReacaoTemComponentesDAO;
import dados.ReconstrucaoDAO;
import entidades.Componente;
import entidades.GeneAssociacao;
import entidades.Ortholog;
import entidades.Reacao;
import entidades.ReacaoTemComponentes;
import entidades.Reconstrucao;
import fasta.UnidFASTA;

@ManagedBean
@ViewScoped
public class JsbmlBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static SBMLDocument sbml;
	
	public JsbmlBean(String _path) {
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

	public List<Reaction> obterListaReactionPeloGenePAO1(String _idGenePAO) {
		try {

			List<Reaction> reactions = new ArrayList<Reaction>();
			
			_idGenePAO = "G_" + _idGenePAO;

			if (sbml != null) {

				Model modelo = sbml.getModel();

				List<Reaction> listaReaction = modelo.getListOfReactions();

				for (Reaction r : listaReaction) {

					// System.out.println(r.getId());

					FBCReactionPlugin mf = (FBCReactionPlugin) r.getPlugin(FBCConstants.shortLabel);

					GeneProductAssociation gpa = mf.getGeneProductAssociation();

					if (gpa != null) {

						// System.out.println(gpa.getId());

						Association ass = gpa.getAssociation();

						List<GeneProductRef> genes = new ArrayList<GeneProductRef>();

						montarListaGenesProductsRefsDeAssociation(ass, genes);

						for (GeneProductRef gpr : genes) {

							// System.out.println(gpr.getGeneProduct());

							if (gpr.getGeneProduct().equals(_idGenePAO)) {

								System.out.println("----achou !!!! ----");
								System.out.println("Reação: " + r.getId() + " : " + r.getName() + " : " + r.getReversible());
								System.out.println("----achou !!!! ----");

								reactions.add(r);
							}
						}

						// System.out.println("-------------------------------------------");
					}
				}
			} else {
				return null;
			}

			return reactions;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<String> obterListaGenesAssociados(Reaction _reaction) {
		
		try {
			
			List<String> genes = new ArrayList<String>();
			
			FBCReactionPlugin mf = (FBCReactionPlugin) _reaction.getPlugin(FBCConstants.shortLabel);

			GeneProductAssociation gpa = mf.getGeneProductAssociation();

			if (gpa != null) {

				Association ass = gpa.getAssociation();

				List<GeneProductRef> gprs = new ArrayList<GeneProductRef>();

				montarListaGenesProductsRefsDeAssociation(ass, gprs);

				for (GeneProductRef gpr : gprs) {
					
					genes.add(gpr.getGeneProduct());
				}
			}
			
			return genes;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void montarListaGenesProductsRefsDeAssociation(Association _association, List<GeneProductRef> _genes) {

		try {

			if (_association instanceof Or) {

				Or or = (Or) _association;

				List<Association> associacoes = or.getListOfAssociations();

				for (Association a : associacoes) {

					montarListaGenesProductsRefsDeAssociation(a, _genes);
				}
			} else if (_association instanceof And) {

				And and = (And) _association;

				List<Association> associacoes = and.getListOfAssociations();

				for (Association a : associacoes) {

					montarListaGenesProductsRefsDeAssociation(a, _genes);
				}
			} else if (_association instanceof GeneProductRef) {

				GeneProductRef gpr = (GeneProductRef) _association;

				_genes.add(gpr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] obterEquacaoDefinicaoDeReaction(Reaction _reaction) {
		try {

			String[] resultado = new String[2];
			
			String def = ""; 
			String eq = "";

			System.out.println("--------------------- LISTA REAGENTES --------------------------------");
			
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
			
			System.out.println("--------------------------------------------------------");
			
			System.out.println("--------------------- LISTA PRODUTOS --------------------------------");
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
			
			System.out.println("--------------------------------------------------------");
			
			System.out.println("DEFINIÇÃO: " + def);
			
			System.out.println("FORMULA: " + eq);
			
			resultado[0] = def;
			resultado[1] = eq;
			
			return resultado;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Componente obterComponenteDeSpeciesReference(SpeciesReference _speciesReference) {
		try {

			Species esp = _speciesReference.getSpeciesInstance();

			FBCSpeciesPlugin fsp = (FBCSpeciesPlugin) esp.getPlugin(FBCConstants.shortLabel);

			String abbreviationC = esp.getId();
			//String compartment = esp.getCompartment();
			String name = esp.getName();
			String formula = fsp.getChemicalFormula();
			int charge = fsp.getCharge();
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
	
	public Reacao obterReacaoDeReaction(Reaction _reaction) {
		try {

			String[] res = obterEquacaoDefinicaoDeReaction(_reaction);
			
			Reacao reacao = new Reacao();
			
			reacao.setAbbreviation(_reaction.getId());
			reacao.setName(_reaction.getName());
			reacao.setDefinition(res[0]);
			reacao.setEquation(res[1]);
			reacao.setReverse(_reaction.getReversible());
			
			//String exp = obterExpressaoAssociationGenesDeComentarios(_reaction);
			
			//reacao.setAssociacao(exp);
			
			return reacao;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	//Obtem as relações entre a reação e o componente, que pode ser tanto reagente quanto produto
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

	public Reconstrucao novaReconstrucaoPeloSbmlPAO1(String _titulo, String _versao, Map<String, UnidFASTA> _unidsFASTA, List<String> _genesNAOachados, Boolean _inferirGenes) {
		try {
			
			ReconstrucaoDAO daoRec = new ReconstrucaoDAO();		
			OrthologDAO daoO = new OrthologDAO();
			
			Set<Reacao> reacoesDaReconstrucao = new HashSet<Reacao>();
			
			//List<Ortholog> orthologs = daoO.listarTodos();
			
			System.out.println(_unidsFASTA.size());
			
			for (String idUnidade : _unidsFASTA.keySet()) {
				
				Ortholog o = daoO.PegarPeloID("fj|" + idUnidade);
				
				if (o == null) {
					_genesNAOachados.add(idUnidade);
				}
				else {
					
					String idCCBH = o.getSequenceIdA();
					String idPao = o.getSequenceIdB();
					
					String[] idP = idPao.split("([|])");
					String[] idC = idCCBH.split("([|])");
					
					List<Reaction> reactions = obterListaReactionPeloGenePAO1(idP[1]);
					
					if (reactions.isEmpty()) {
						_genesNAOachados.add(idC[1]);
					}
					else {
						for (Reaction r : reactions) {
							
							Reacao reacao = inserirObjetosNaBase(r);
							
							reacoesDaReconstrucao.add(reacao);
						}
					}
				}
			}
			
			if (_inferirGenes) {
				
				List<Reaction> reactions = sbml.getModel().getListOfReactions();
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
						reactionInferidas.add(reaction);
					}
				}
				
				for (Reaction reaction : reactionInferidas) {
					
					System.out.println("Reação inferida no modelo: " + reaction.getId());
					
					Reacao reacao = inserirObjetosNaBase(reaction);
					
					reacoesDaReconstrucao.add(reacao);
				}
			}
			
			Reacao reacao = gerarReacaoBiomassaDeModelo("R_PA_Biomass6_DM");
			reacoesDaReconstrucao.add(reacao);
			
			Reconstrucao rec = new Reconstrucao();
			rec.setTitulo(_titulo);
			rec.setVersion(_versao);
			rec.setData(new Date());
			//rec.setReacoes(reacoesDaReconstrucao);
			
			daoRec.Inserir(rec);
			
			return rec;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Reacao inserirObjetosNaBase (Reaction _reaction) {
		
		try {
			
			ReacaoDAO daoR = new ReacaoDAO();
			ComponenteDAO daoC = new ComponenteDAO();
			ReacaoTemComponentesDAO daoRTC = new ReacaoTemComponentesDAO();
			GeneAssociacaoDAO daoGA = new GeneAssociacaoDAO();
			
			Reacao reacao = new Reacao();
			
			if(!daoR.ExisteReacao(_reaction.getId())) {
				
				reacao = obterReacaoDeReaction(_reaction);
				daoR.Inserir(reacao);
			}
			else {
				
				reacao = daoR.PegarPeloID(_reaction.getId());
			}
			
			List<String> genesAssociados = obterListaGenesAssociados(_reaction);
			
			for (String idGA : genesAssociados) {
				
				if (!daoGA.ExisteGeneAssociacao(idGA)) {
					
					GeneAssociacao ga = new GeneAssociacao(idGA, reacao);
					
					daoGA.Inserir(ga);
				}
			}
			
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
	
			System.out.println(reacao);
			
			return reacao;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public boolean gerarSBMLdeReconstrucao(Long _idReconstrucao, String _pathSaida) {
		try {
			
			//MONTAGEM DO SBML ----------------------------------------------------
			int level = 3, version = 1;
			
			//SBMLDocument doc = new SBMLDocument(level, version);
			
			//Classe construtora de objetos do SBML
			ModelBuilder construtor = new ModelBuilder(level, version);

			//Criação do modelo, com id, nome e versão
		    Model modelo = construtor.buildModel("CCBH4851", "Pseudomonas aeruginosa CCBH 4851 - v1");
		    
		    //Classe responsavél po acrescentar função de análise de balanço de fluxo
		    FBCModelPlugin pluginModelo = (FBCModelPlugin) modelo.getPlugin(FBCConstants.shortLabel);
		    pluginModelo.setStrict(true);

		    //Criação das unidades de medidas para o modelo
		    /*
		    UnitDefinition udVol = construtor.buildUnitDefinition("volume", "Volume units");
		    buildUnit(udVol, 1d, -3, Unit.Kind.LITRE, 1d);
		    modelo.setVolumeUnits(udVol);
		    
		    UnitDefinition udTim = construtor.buildUnitDefinition("time", "Time units");
		    buildUnit(udTim, 1d, 0, Unit.Kind.SECOND, 1d);
		    modelo.setTimeUnits(udTim);
		    
		    UnitDefinition udSub = construtor.buildUnitDefinition("substance", "Substance units");
		    buildUnit(udSub, 1d, -3, Unit.Kind.MOLE, 1d);
		    modelo.setSubstanceUnits(udSub);
		    
		    UnitDefinition udExt = construtor.buildUnitDefinition("extent", "Extent units");
		    udExt.multiplyWith(modelo.getSubstanceUnitsInstance().clone());
		    udExt.divideBy(modelo.getTimeUnitsInstance().clone());
		    modelo.setExtentUnits(udExt);
		    */
		    UnitDefinition ud = construtor.buildUnitDefinition("mmol_per_gDW_per_hr", "");
		    construtor.buildUnit(ud, 1d, -3, Unit.Kind.MOLE, 1d);
		    construtor.buildUnit(ud, 1d, 0, Unit.Kind.GRAM, -1d);
		    construtor.buildUnit(ud, 3600d, 0, Unit.Kind.SECOND, -1d);
		    
		    //Criação de parâmetros de fluxo mínimo e máximo
		    Parameter lowerFluxBound = construtor.buildParameter("lb", "lower flux bound", 0d, true, modelo.getExtentUnits());
		    Parameter upperFluxBound = construtor.buildParameter("ub", "upper flux bound", 1000d, true, modelo.getExtentUnits());
		    
		    //Criação de possíveis compartimento do modelo que está sendo gerado
		    Compartment compartimentC = construtor.buildCompartment("c", true, "cytosol", 3d, 1d, modelo.getVolumeUnits());
		    Compartment compartimentE = construtor.buildCompartment("e", true, "extracellular", 3d, 1d, modelo.getVolumeUnits());
		    //Compartment compartimentP = construtor.buildCompartment("p", true, "periplasm", 3d, 1d, modelo.getVolumeUnits());
		    
			ReacaoDAO daoR = new ReacaoDAO(); 
			//ComponenteDAO daoC = new ComponenteDAO();
			GeneAssociacaoDAO daoGA = new GeneAssociacaoDAO();
			
			List<Reacao> reacoes = daoR.listarTodos();
			
			for (Reacao r : reacoes) {
				
				List<ReacaoTemComponentes> rtcs = (List<ReacaoTemComponentes>) r.getRhcs();
				
				Reaction reaction = construtor.buildReaction(r.getAbbreviation(), r.getName(), compartimentC, false, true);
				
				for (ReacaoTemComponentes rtc : rtcs) {
					
					Componente comp = rtc.getComponente();
					
					if (!modelo.containsSpecies(comp.getAbbreviation())) {
						
						String id = comp.getAbbreviation();
						
						String compartimento = id.substring(id.length() - 2, id.length());
						
						Species s;
						if (compartimento.equals("_c")) {
							
							s = construtor.buildSpecies(comp.getAbbreviation(), comp.getName(), compartimentC, false, false, false, 1d, ud);
						}
						else {
							
							s = construtor.buildSpecies(comp.getAbbreviation(), comp.getName(), compartimentE, false, false, false, 1d, ud);
						}
						
						System.out.println(s);
					}
						
					Species s = modelo.getSpecies(comp.getAbbreviation());
					
					if (rtc.getTipo_P_ou_R() == 'r') {
						
						Pair<Double, Species> reagente = new Pair<Double, Species>();
						reagente.setKey(rtc.getEstequiometria());
						reagente.setValue(s);
						construtor.buildReactants(reaction, reagente);
					}
					else {
						
						Pair<Double, Species> produto = new Pair<Double, Species>();
						produto.setKey(rtc.getEstequiometria());
						produto.setValue(s);
						construtor.buildProducts(reaction, produto);
					}	
				}
			
				List<GeneAssociacao> genesAssociados = daoGA.obterGenesAssociacaoPorIdReacao(r.getAbbreviation());
				
				FBCReactionPlugin reactionPlugin = (FBCReactionPlugin) reaction.getPlugin(FBCConstants.shortLabel);
				
				if (genesAssociados.size() > 0) {
					
					//Queue<String> fila = PilhaUtil.obterFilaPosFixa("");
					
					//Arvore arvore = ArvoreUtil.criaArvore(fila);
					
					GeneProductAssociation gpa = reactionPlugin.createGeneProductAssociation();
					
					Association association = new Or(level, version);
					
					And and = new And(level, version);
					
					for (GeneAssociacao ga : genesAssociados) {
						
						GeneProductRef gpr = new GeneProductRef(level, version);
						
						gpr.setGeneProduct(ga.getId());
						
						and.addAssociation(gpr);
						
						GeneProduct gp = new GeneProduct();
						
						gp.setId(ga.getId());
						gp.setName(ga.getId().substring(2, 8));
						gp.setLabel(ga.getId().substring(2, 8));
						
						pluginModelo.getListOfGeneProducts().add(gp);
					}
					
					((Or)association).addAssociation(and);
					
					gpa.setAssociation(association);
				}
				
				reactionPlugin.setLowerFluxBound(lowerFluxBound);
			    reactionPlugin.setUpperFluxBound(upperFluxBound);
			}
			
			if (modelo.containsReaction("R_PA_Biomass6_DM")) {
				
				
				Objective objetivo = new Objective();
				
				objetivo.setId("obj");
				objetivo.setType(Type.MAXIMIZE);
				
				FluxObjective fo = new FluxObjective();
				fo.setId("um");
				fo.setReaction("R_PA_Biomass6_DM");
				fo.setCoefficient(1);
				
				objetivo.addFluxObjective(fo);
				
				pluginModelo.addObjective(objetivo);
				
				pluginModelo.setActiveObjective(objetivo);
			}
			
			SBMLDocument doc = construtor.getSBMLDocument();
		    doc.addDeclaredNamespace("html", JSBML.URI_XHTML_DEFINITION);
		    
		    //String docStr = new SBMLWriter().writeSBMLToString(doc);
		    
		    new SBMLWriter().writeSBMLToFile(doc, _pathSaida);
		      
		    //System.out.println(docStr);
		    
		    
		    //System.out.println("Re-reading the model.");
		    //SBMLDocument doc2 = new SBMLReader().readSBMLFromString(docStr);
		      
		    //Assert.assertTrue(((FBCModelPlugin) doc2.getModel().getPlugin(FBCConstants.shortLabel)).isSetStrict());  
		    //Assert.assertTrue(((FBCModelPlugin) doc2.getModel().getPlugin(FBCConstants.shortLabel)).getStrict() == true);
		    
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Reacao gerarReacaoBiomassaDeModelo(String _idReacaoBiomassa) {
		try {
			
			ReacaoDAO daoR = new ReacaoDAO();
			ComponenteDAO daoC = new ComponenteDAO();
			ReacaoTemComponentesDAO daoRTC = new ReacaoTemComponentesDAO();
			GeneAssociacaoDAO daoGA = new GeneAssociacaoDAO();
			
			_idReacaoBiomassa = "R_PA_Biomass6_DM";
			
			Reaction r = obterReactionPeloID(_idReacaoBiomassa);
			
			Reacao reacao = obterReacaoDeReaction(r);
			
			if(!daoR.ExisteReacao(reacao.getAbbreviation())) {	
				daoR.Inserir(reacao);
			}
			
			List<String> genesAssociados = obterListaGenesAssociados(r);
			
			for (String idGA : genesAssociados) {
				
				if (!daoGA.ExisteGeneAssociacao(idGA)) {
					
					GeneAssociacao ga = new GeneAssociacao(idGA, reacao);
					
					daoGA.Inserir(ga);
				}
			}
			
			List<SpeciesReference> speciesReferences = r.getListOfReactants();
			
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
			
			speciesReferences = r.getListOfProducts();
			
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

			System.out.println(reacao);
			
			return reacao;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
