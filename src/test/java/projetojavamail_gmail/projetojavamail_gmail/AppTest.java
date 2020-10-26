package projetojavamail_gmail.projetojavamail_gmail;

import org.junit.Test;

/*AVISO IMPORTANTE:
 
  Para que o projeto funcione, siga os seguintes passos dentro do seu Gmail:
  
  1. Acesse Manage your Google Account (Gerenciar sua conta do Google);
  2. A seguir, clique em Segurança;
  3. Em 'Acesso a app menos seguro', clique em 'Ativar acesso'; e
  4. Por fim, ative clicando em 'Permitir aplicativos menos seguros'.
*/
public class AppTest {

	@Test
	public void testeEmail() throws Exception {

		ObjetoEnviarEmail email = new ObjetoEnviarEmail(
			"destinatario1@yahoo.com.br",
			"Empresa A1",
			"Envio de email usando Java",
			"Olá developer! Esse é um teste de envio de email usando JavaMail!");
		
		email.enviarEmail();
	}
}
