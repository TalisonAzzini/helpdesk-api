package com.helpdesk.helpdesk_api.controller;

import com.helpdesk.helpdesk_api.model.Chamado;
import com.helpdesk.helpdesk_api.service.ChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chamados")
public class ChamadoController {
    private final ChamadoService chamadoService;

    @PostMapping
    public ResponseEntity<Chamado> abrirChamado(@RequestBody Chamado chamado) {
        Chamado novoChamado = chamadoService.abrirChamado(
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getPrioridade(),
                chamado.getTecnico(),
                chamado.getSolicitante()
        );
        return ResponseEntity.status(201).body(novoChamado);
    }

    @GetMapping
    public ResponseEntity<List<Chamado>> listarChamados() {
        return ResponseEntity.ok(chamadoService.listarChamados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chamado> buscarChamadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarChamadoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chamado> atualizarChamado(@PathVariable Long id, @RequestBody Chamado dadosAtualizados, @RequestParam Long tecnicoId) {
        return  ResponseEntity.ok(chamadoService.atualizarChamado(id, dadosAtualizados, tecnicoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarChamado(@PathVariable Long id) {
        chamadoService.deletarChamado(id);
        return ResponseEntity.noContent().build();
    }
}
