package controle;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class ClientAxisBrendaBean {

	public String[] obterSequenciaPorOrganismoAndEC() {
		
		try {
			
			String parameters = "thiago.ramos@ioc.fiocruz.br,35e097c2592e6f1f60956fda568e90581153158a4bd09f51f1e4e08a83f93d6f,ecNumber*3.2.1.89#organism*Escherichia coli";
			Service service = new Service();
			Call call = (Call) service.createCall();
			String endpoint = "http://www.brenda-enzymes.org/soap/brenda_server.php";
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
		
			call.setOperationName(new QName("http://soapinterop.org/", "getSequence"));
			String resultString = (String) call.invoke(new Object[] { parameters });
			
			//System.out.println(resultString);
			
			String[] resultados = resultString.split("");
			
			return resultados;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
