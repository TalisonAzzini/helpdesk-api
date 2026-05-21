package com.helpdesk.helpdesk_api.services;

import com.helpdesk.helpdesk_api.dtos.UsuarioResponse;
import com.helpdesk.helpdesk_api.enums.Cargo;
import com.helpdesk.helpdesk_api.models.Usuario;
import com.helpdesk.helpdesk_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    public UsuarioResponse buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        return UsuarioResponse.from(usuario);
    }

    public UsuarioResponse atualizarUsuario(Long id, Usuario dadosUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        usuario.setNome(dadosUsuario.getNome());
        usuario.setEmail(dadosUsuario.getEmail());
        usuario.setCargo(dadosUsuario.getCargo());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return UsuarioResponse.from(usuarioAtualizado);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void validarTecnico(Usuario usuario) {
        if (usuario.getCargo() != Cargo.TECNICO) {
            throw new BadCredentialsException("Usuário não tem cargo de técnico.");
        }
    }
}