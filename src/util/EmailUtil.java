package util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {

	public void enviaEmail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthentication("cursystem@gmail.com", "ccbh4851");
		//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setFrom("cursystem@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("E-mail teste do sistema ... :-)");
		email.addTo("gianninimail@gmail.com");
		email.send();
	}
	
	public void enviaEmail(String _msg, String _destino) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthentication("cursystem@gmail.com", "ccbh4851");
		//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setFrom("cursystem@gmail.com");
		email.setSubject("E-mail padr�o do Sistema");
		email.setMsg("_msg");
		email.addTo(_destino);
		email.send();
	}
	
	public static void enviaEmail(String _titulo, String _msg, String _destino) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthentication("cursystem@gmail.com", "ccbh4851");
		//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setFrom("cursystem@gmail.com");
		email.setSubject(_titulo);
		email.setMsg(_msg);
		email.addTo(_destino);
		email.send();
	}
	
	public static void enviaEmail(String _titulo, String _msg, String _destino, String _pathArquivo, String _nomeArquivo) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthentication("ccbh4851system@gmail.com", "ccbh4851");
		//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setFrom("ccbh4851system@gmail.com");
		email.setSubject(_titulo);
		email.setMsg(_msg);
		email.addTo(_destino);
		
		
		EmailAttachment anexo = new EmailAttachment();
		anexo.setPath(_pathArquivo);
		anexo.setDisposition(EmailAttachment.ATTACHMENT);
		anexo.setName(_nomeArquivo);
		
		email.attach(anexo);
		
		email.send();
	}
}
