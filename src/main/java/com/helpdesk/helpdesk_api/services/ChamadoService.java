package com.helpdesk.helpdesk_api.services;

import com.helpdesk.helpdesk_api.dtos.ChamadoRequest;
import com.helpdesk.helpdesk_api.dtos.ChamadoResponse;
import com.helpdesk.helpdesk_api.models.*;
import com.helpdesk.helpdesk_api.enums.Status;
import com.helpdesk.helpdesk_api.repositories.ChamadoRepository;
import com.helpdesk.helpdesk_api.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {
    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public ChamadoResponse abrirChamado(ChamadoRequest dadosChamado) {
        Usuario solicitante = usuarioRepository.findById(dadosChamado.solicitanteId())
                .orElseThrow(() -> new UsernameNotFoundException("Solicitante não encontrado"));

        Usuario tecnico = usuarioRepository.findById(dadosChamado.tecnicoId())
                .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado."));
        usuarioService.validarTecnico(tecnico);

        Chamado chamado = new Chamado();
        chamado.setTitulo(dadosChamado.titulo());
        chamado.setDescricao(dadosChamado.descricao());
        chamado.setPrioridade(dadosChamado.prioridade());
        chamado.setStatus(Status.ABERTO);
        chamado.setDataCriado(LocalDateTime.now());
        chamado.setSolicitante(solicitante);
        chamado.setTecnico(tecnico);

        Chamado chamadoSalvo = chamadoRepository.save(chamado);

        return ChamadoResponse.from(chamadoSalvo);
    }

    public List<ChamadoResponse> listarChamados() {
        return chamadoRepository.findAll()
                .stream()
                .map(ChamadoResponse::from)
                .toList();
    }

    private Chamado buscarChamadoEntidade(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chamado não encontrado."));
    }

    public ChamadoResponse buscarChamadoPorId(Long id) {
        return ChamadoResponse.from(chamadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chamado não encontrado.")));
    }

    public ChamadoResponse atualizarChamado(Long id, ChamadoRequest dadosChamado) {
        Usuario tecnico = usuarioRepository.findById(dadosChamado.tecnicoId())
                .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado."));
        usuarioService.validarTecnico(tecnico);

        Chamado chamado = buscarChamadoEntidade(id);
        chamado.setTitulo(dadosChamado.titulo());
        chamado.setDescricao(dadosChamado.descricao());
        chamado.setPrioridade(dadosChamado.prioridade());
        chamado.setStatus(dadosChamado.status());
        chamado.setTecnico(tecnico);

        if (dadosChamado.status() == Status.FECHADO) {
            chamado.setDataFechado(LocalDateTime.now());
        }

        Chamado chamadoAtualizado = chamadoRepository.save(chamado);

        return ChamadoResponse.from(chamadoAtualizado);
    }

    public void deletarChamado(Long id) {
        chamadoRepository.deleteById(id);
    }

}