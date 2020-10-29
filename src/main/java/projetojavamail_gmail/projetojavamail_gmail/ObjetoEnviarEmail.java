package projetojavamail_gmail.projetojavamail_gmail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviarEmail {

	private String loginUsuario = "remetente@gmail.com";
	private String senhaUsuario = "senharemetente";

	private List<String> listaDestinatarios = Arrays.asList("destinatario1@gmail.com",
			"destinatario2@yahoo.com.br");
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviarEmail(String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHtml) throws Exception {
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

			if (envioHtml) {
				message.setContent(textoEmail, "text/html; charset=utf-8");
			} else {
				message.setText(textoEmail);
			}

			cont++;
			System.out.println("Enviando para destinatário " + cont);
			Transport.send(message);
		}

		System.out.println("Envio finalizado!");

	}

	public void enviarEmailAnexo(boolean envioHtml) throws Exception {
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

			MimeBodyPart corpoEmail = new MimeBodyPart();

			if (envioHtml) {
				corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
			} else {
				corpoEmail.setText(textoEmail);
			}

			List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
			arquivos.add(simuladorPdf()); // Aqui poderia ser um certificado
			arquivos.add(simuladorPdf()); // Aqui uma Nota Fiscal
			arquivos.add(simuladorPdf()); // Aqui um documento de texto
			arquivos.add(simuladorPdf()); // Aqui poderia ter uma imagem
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(corpoEmail);
			
			int index = 0;
			for(FileInputStream fileInputStream : arquivos) {
				MimeBodyPart anexoEmail = new MimeBodyPart();
				
				/*Na linha abaixo está application/pdf, mas eu posso mudar a extensão para docx
				 * e ficaria application/docx se eu quisesse enviar documentos word por exemplo,
				 * o mesmo sentido seria para uma imagem (png, jpg, ...)*/
				
				anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(
						fileInputStream, "application/pdf")));
				
				anexoEmail.setFileName("anexoemail" + index + ".pdf");
				
				multipart.addBodyPart(anexoEmail);
				index++;
			}

			message.setContent(multipart);
			cont++;
			System.out.println("Enviando para destinatário " + cont);
			Transport.send(message);
		}

		System.out.println("Envio finalizado!");

	}

	private FileInputStream simuladorPdf() throws Exception {
		Document document = new Document();

		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Esse é o texto escrito dentro do documento PDF"));
		document.close();

		return new FileInputStream(file);
	}

}
