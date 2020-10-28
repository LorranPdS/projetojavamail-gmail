package projetojavamail_gmail.projetojavamail_gmail;

import org.junit.Test;

public class AppTest {

	@Test
	public void testeEmail() throws Exception {

		ObjetoEnviarEmail email = new ObjetoEnviarEmail("emailremetente@gmail.com", "Teste Email",
				"Email enviado com sucesso sem que você saiba mais quem além de você recebeu!");

		email.enviarEmail();
	}
}
