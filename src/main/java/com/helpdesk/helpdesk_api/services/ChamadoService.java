package com.helpdesk.helpdesk_api.services;

import com.helpdesk.helpdesk_api.enums.Cargo;
import com.helpdesk.helpdesk_api.model.*;
import com.helpdesk.helpdesk_api.enums.Status;
import com.helpdesk.helpdesk_api.enums.Prioridade;
import com.helpdesk.helpdesk_api.repositories.ChamadoRepository;
import com.helpdesk.helpdesk_api.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {
    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;

    public Chamado abrirChamado(String titulo, String descricao, Prioridade prioridade, Usuario tecnico, Usuario solicitante) {
        validarTecnico(tecnico);

        Chamado novoChamado = new Chamado();
        novoChamado.setTitulo(titulo);
        novoChamado.setDescricao(descricao);
        novoChamado.setPrioridade(prioridade);
        novoChamado.setStatus(Status.ABERTO);
        novoChamado.setDataCriado(LocalDateTime.now());
        novoChamado.setTecnico(tecnico);
        novoChamado.setSolicitante(solicitante);

        return chamadoRepository.save(novoChamado);
    }

    public Chamado atualizarChamado(Long id, Chamado dadosChamado, Long tecnicoId) {
        Usuario tecnico = usuarioRepository.findById(tecnicoId)
                .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado."));
        validarTecnico(tecnico);

        Chamado chamado = buscarChamadoPorId(id);
        chamado.setTitulo(dadosChamado.getTitulo());
        chamado.setDescricao(dadosChamado.getDescricao());
        chamado.setPrioridade(dadosChamado.getPrioridade());
        chamado.setStatus(dadosChamado.getStatus());
        chamado.setTecnico(tecnico);

        if (dadosChamado.getStatus().equals(Status.FECHADO)) {
            chamado.setDataFechado(LocalDateTime.now());
        }

        return chamadoRepository.save(chamado);
    }

    public List<Chamado> listarChamados() {
        return chamadoRepository.findAll();
    }

    public Chamado buscarChamadoPorId(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chamado não encontrado."));
    }

    public void deletarChamado(Long id) {
        chamadoRepository.deleteById(id);
    }

    private void validarTecnico(Usuario usuario) {
        if (usuario.getCargo() != Cargo.TECNICO) {
            throw new BadCredentialsException("Usuário não tem cargo de técnico.");
        }
    }
}
