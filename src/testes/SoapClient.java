package testes;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import dados.EnzimaBrendaDAO;
import entidades.EnzimaBrenda;

import javax.xml.namespace.QName;
import java.security.MessageDigest;

public class SoapClient {
	// private static StringBuffer hexString;

	public static void main(String[] args) throws Exception {
		
		//String parameterSeq = "sequence*MGTQGKVIKCKAAIAWKTGSPLCIEEIEVSPPKACEVRIQVIATCVCPTDINATDPKKKALFPVVLGHECAGIVESVGPGVTNFKPGDKVIPFFAPQCKRCKLCLSPLTNLCGKLRNFKYPTIDQELMEDRTSRFTCKGRSIYHFMGVSSFSQYTVVSEANLARVDDEANLERVCLIGCGFSSGYGAAINTAKVTPSSTCAVFGLGCVGLSAIIGCKIAGASRIIAIDINGEKFPKAKALGATDCLNPRELDKPVQDVITELTAGGVDYSLDCAGTAQTLKAAVDCTVLGWGSCTVVGAKVDKMTIPTVDVILGRSINGTFFGGWKSVDSVPNLVSDYKNKKFDLDLLVTHALPFESINDAIDLMKEGKSIRTILTF";
		String parameterOrganismPAeruginosa = "#organism*Pseudomonas aeruginosa";
		String parameterOrganismEColi = "#organism*Escherichia coli";
		String user = "thiago.ramos@ioc.fiocruz.br";
		String password = "thiago";
		
		Service service = new Service();
		Call call = (Call) service.createCall();

		String endpoint = "http://www.brenda-enzymes.org/soap/brenda_server.php";

		
		// 86c02cfcff7fc2fc71b250119b2c76b259971131992e24491bc8b954011fa0
		// 86c02cfcff7fc2fc71b250119b2c76b259971131992e24491bc8b954011fa0
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.update(password.getBytes());

		byte byteData[] = md.digest();

		StringBuffer passwordSHA = new StringBuffer();

		for (int i = 0; i < byteData.length; i++) {

			String hex = Integer.toHexString(0xff & byteData[i]);

			if (hex.length() == 1) {
				passwordSHA.append('0');
			} else {
				passwordSHA.append(hex);
			}
		}
		
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		String parameters = user + "," + passwordSHA + "," + "";
		call.setOperationName(new QName("http://soapinterop.org/", "getEcNumbersFromSequence"));
		String resultString = (String) call.invoke(new Object[] { parameters });
		
		System.out.println(resultString);
		
		String[] ecs = resultString.split("!");
		System.out.println(ecs.length);
		for (int i = 1; i < ecs.length; i++) {
			
			System.out.println(ecs[i]);
			
			EnzimaBrendaDAO dao = new EnzimaBrendaDAO();
			//2.7.7.48 / 3.2.1.18 / 4.1.1.39
			if (!dao.ExisteEnzimaBrenda(ecs[i]) && !ecs[i].equals("2.7.7.48")) {
			
				service = new Service();
				
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpoint));
				call.setOperationName(new QName("http://soapinterop.org/", "getSequence"));
				
				String parameterEC = "ecNumber*" + ecs[i];
				String parameters2 = user + "," + passwordSHA + "," + parameterEC + parameterOrganismPAeruginosa;
				
				resultString = (String) call.invoke(new Object[] { parameters2 });
				
				if (!resultString.equals("")) {
					
					String[] campos = resultString.split("#");
					
					String ec = campos[0].substring(9);
					String seq = campos[1].substring(9);
					
					EnzimaBrenda e = new EnzimaBrenda(ec, seq);
					
					dao.Inserir(e);
					
					System.out.println("Inserido PA: EC=" + ec + " / SEQ=" + seq);
				}
				else {
					
					service = new Service();
					
					call = (Call) service.createCall();
					call.setTargetEndpointAddress(new java.net.URL(endpoint));
					call.setOperationName(new QName("http://soapinterop.org/", "getSequence"));
					
					String parameters3 = user + "," + passwordSHA + "," + parameterEC + parameterOrganismEColi;
					
					resultString = (String) call.invoke(new Object[] { parameters3 });
					
					if (!resultString.equals("")) {
						
						String[] campos = resultString.split("#");
						
						String ec = campos[0].substring(9);
						String seq = campos[1].substring(9);
						
						EnzimaBrenda e = new EnzimaBrenda(ec, seq);
						
						dao.Inserir(e);
						
						System.out.println("Inserido EColi: EC=" + ec + " / SEQ=" + seq);
					}
					else {
						
						service = new Service();
				
						call = (Call) service.createCall();
						call.setTargetEndpointAddress(new java.net.URL(endpoint));
						call.setOperationName(new QName("http://soapinterop.org/", "getSequence"));
						
						String parameters4 = user + "," + passwordSHA + "," + parameterEC;
						
						resultString = (String) call.invoke(new Object[] { parameters4 });
						
						if (!resultString.equals("")) {
							
							String[] campos = resultString.split("#");
							
							String ec = campos[0].substring(9);
							String seq = campos[1].substring(9);
							
							System.out.println(seq.length());
							
							EnzimaBrenda e = new EnzimaBrenda(ec, seq);
							
							dao.Inserir(e);
							
							System.out.println("Inserido Generic: EC=" + ec + " / SEQ=" + seq);
						}
					}
				}
			}
		}
	}
}