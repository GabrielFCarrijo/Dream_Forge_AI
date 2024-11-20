package infrastructure.service;

import infrastructure.repositories.UsuarioRepository;
import infrastructure.repositories.entities.Usuario;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    @Channel("password-reset")
    @Broadcast
    Emitter<String> passwordResetEmitter;

    @Transactional
    public void cadastrarUsuario(String login, String senha, String email) {
        if (usuarioRepository.findByLogin(login).isPresent()) {
            throw new IllegalArgumentException("Login já existe!");
        }

        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setEmail(email);

        usuarioRepository.persist(usuario);
    }


    @Transactional
    public boolean validarLogin(String login, String senha) {
        return usuarioRepository.findByLogin(login)
                .map(usuario -> senha.equals(usuario.getSenha()))
                .orElse(false);
    }

    @Transactional
    public void enviarEmailRecuperacaoSenha(String login) {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Email não encontrado!");
        }

        String mensagem = String.format("Usuário: %s. Link para resetar a senha.", usuario.get().getEmail());
        CompletableFuture.runAsync(() -> {
            passwordResetEmitter.send(mensagem);
        });
    }

}
