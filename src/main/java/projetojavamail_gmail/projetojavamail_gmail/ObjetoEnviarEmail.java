package projetojavamail_gmail.projetojavamail_gmail;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ObjetoEnviarEmail {

	private String loginUsuario = "emailremetente@gmail.com";
	private String senhaUsuario = "senharemetente";

	private List<String> listaDestinatarios = Arrays.asList("destinatario1@gmail.com"
			, "destinatario2@yahoo.com.br");
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviarEmail(String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail() throws Exception {
		Properties properties = new Properties();
		properties.put("mail.smtp.connectiontimeout", "1000");
		properties.put("mail.smtp.timeout", "5000");
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true"); // Autorização
		properties.put("mail.smtp.starttls", "true"); // Autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com"); // Servidor Gmail
		properties.put("mail.smtp.port", "465"); // Porta do servidor
		properties.put("mail.smtp.socketFactory.port", "465"); // Especifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Classe socket de conexão
																							// ao SMTP

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(loginUsuario, senhaUsuario);
			}
		});

		int cont = 0;
		for (String destino : listaDestinatarios) {
		
		Address[] toUser = InternetAddress.parse(destino);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(loginUsuario, nomeRemetente));
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject(assuntoEmail);
		message.setText(textoEmail);
		
		cont++;
		System.out.println("Enviando para destinatário " + cont);
		Transport.send(message);
		}
		System.out.println("Mensagens enviadas com sucesso!");

	}

}
