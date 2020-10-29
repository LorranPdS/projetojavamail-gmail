package projetojavamail_gmail.projetojavamail_gmail;

import org.junit.Test;

public class AppTest {

	@Test
	public void testeEmail() throws Exception {
		
		StringBuilder sbTextEmail = new StringBuilder();
		sbTextEmail.append("Olá, <br/><br/>");
		sbTextEmail.append("<h2>Email customizado com html</h2><br/><br/>");
		sbTextEmail.append("Para acessar o YouTube, clique no botão abaixo.<br/><br/>");
		
		// Primeiro faça o código numa página HTML, se ficar OK, cole o código abaixo
		sbTextEmail.append("<a href=\"https://www.youtube.com/?hl=pt&gl=BR\" target=\"_blank\">"
				+ "<input type=\"button\" value=\"YouTube\"></a>");

		ObjetoEnviarEmail email = new ObjetoEnviarEmail("REMETENTE ABC", "Assunto do Email",
				sbTextEmail.toString());

		email.enviarEmail(true);
	}
}
