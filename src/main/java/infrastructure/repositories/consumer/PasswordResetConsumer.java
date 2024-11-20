package infrastructure.repositories.consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.Mail;

@ApplicationScoped
public class PasswordResetConsumer {

    @Inject
    Mailer mailer;

    @Incoming("password-reset")
    public void onPasswordResetMessage(String message) {
        System.out.println("Mensagem recebida do Kafka: " + message);

        String[] partes = message.split(": ");
        String email = partes.length > 1 ? partes[1] : "";
        if (!email.isEmpty()) {
            enviarEmailRecuperacaoSenha(email);
        }
    }


    private void enviarEmailRecuperacaoSenha(String email) {
        System.out.println("Enviando e-mail para: " + email);
        String corpoEmail = "Aqui está o link para redefinir sua senha";

        Mail emailMessage = Mail.withText(email,
                "Recuperação de Senha",
                corpoEmail);

        mailer.send(emailMessage);
    }

}
