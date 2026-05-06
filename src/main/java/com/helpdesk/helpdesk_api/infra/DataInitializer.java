package com.helpdesk.helpdesk_api.infra;

import com.helpdesk.helpdesk_api.enums.Cargo;
import com.helpdesk.helpdesk_api.model.Usuario;
import com.helpdesk.helpdesk_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByCargo(Cargo.ROOT).isEmpty()) {
            Usuario root = new Usuario();

            root.setNome("Root");
            root.setEmail("root@helpdesk.com");
            root.setSenha(bCryptPasswordEncoder.encode("root123"));
            root.setCargo(Cargo.ROOT);

            usuarioRepository.save(root);
            System.out.println("Usuário root criado com sucesso.");
        }
    }
}
