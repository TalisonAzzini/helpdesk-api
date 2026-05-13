package com.helpdesk.helpdesk_api.services;

import com.helpdesk.helpdesk_api.dtos.ChamadoResponse;
import com.helpdesk.helpdesk_api.dtos.UsuarioResponse;
import com.helpdesk.helpdesk_api.models.*;
import com.helpdesk.helpdesk_api.enums.Status;
import com.helpdesk.helpdesk_api.enums.Prioridade;
import com.helpdesk.helpdesk_api.repositories.ChamadoRepository;
import com.helpdesk.helpdesk_api.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {
    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public ChamadoResponse abrirChamado(String titulo, String descricao, Prioridade prioridade, Long tecnicoId, Long solicitanteId) {
        Usuario tecnico = usuarioRepository.findById(tecnicoId)
                        .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado"));
        usuarioService.validarTecnico(tecnico);

        Usuario solicitante = usuarioRepository.findById(solicitanteId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitante não encontrado"));

        Chamado novoChamado = new Chamado();
        novoChamado.setTitulo(titulo);
        novoChamado.setDescricao(descricao);
        novoChamado.setPrioridade(prioridade);
        novoChamado.setStatus(Status.ABERTO);
        novoChamado.setDataCriado(LocalDateTime.now());
        novoChamado.setTecnico(tecnico);
        novoChamado.setSolicitante(solicitante);

        return toResponse(chamadoRepository.save(novoChamado));
    }

    public ChamadoResponse atualizarChamado(Long id, Chamado dadosChamado, Long tecnicoId) {
        Usuario tecnico = usuarioRepository.findById(tecnicoId)
                .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado."));
        usuarioService.validarTecnico(tecnico);

        Chamado chamado = buscarChamadoEntidade(id);
        chamado.setTitulo(dadosChamado.getTitulo());
        chamado.setDescricao(dadosChamado.getDescricao());
        chamado.setPrioridade(dadosChamado.getPrioridade());
        chamado.setStatus(dadosChamado.getStatus());
        chamado.setTecnico(tecnico);

        if (dadosChamado.getStatus().equals(Status.FECHADO)) {
            chamado.setDataFechado(LocalDateTime.now());
        }

        return toResponse(chamadoRepository.save(chamado));
    }

    public List<ChamadoResponse> listarChamados() {
        return chamadoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Chamado buscarChamadoEntidade(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chamado não encontrado."));
    }
    public ChamadoResponse buscarChamadoPorId(Long id) {
        return toResponse(chamadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chamado não encontrado.")));
    }

    public void deletarChamado(Long id) {
        chamadoRepository.deleteById(id);
    }

    private ChamadoResponse toResponse(Chamado chamado) {
        return new ChamadoResponse(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getPrioridade(),
                chamado.getStatus(),
                chamado.getDataCriado(),
                chamado.getDataFechado(),
                new UsuarioResponse(
                        chamado.getSolicitante().getId(),
                        chamado.getSolicitante().getNome(),
                        chamado.getSolicitante().getEmail(),
                        chamado.getSolicitante().getCargo()
                ),
                new UsuarioResponse(
                        chamado.getTecnico().getId(),
                        chamado.getTecnico().getNome(),
                        chamado.getTecnico().getEmail(),
                        chamado.getTecnico().getCargo()
                )
        );
    }
}
