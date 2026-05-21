package com.helpdesk.helpdesk_api.controllers;

import com.helpdesk.helpdesk_api.dtos.ChamadoRequest;
import com.helpdesk.helpdesk_api.dtos.ChamadoResponse;
import com.helpdesk.helpdesk_api.services.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chamados")
public class ChamadoController {
    private final ChamadoService chamadoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'GERENTE', 'SUPERVISOR', 'TECNICO')")
    public ResponseEntity<ChamadoResponse> abrirChamado(@RequestBody @Valid ChamadoRequest dadosChamado) {
        return ResponseEntity.status(201).body(chamadoService.abrirChamado(dadosChamado));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'GERENTE', 'SUPERVISOR', 'TECNICO')")
    public ResponseEntity<List<ChamadoResponse>> listarChamados() {
        return ResponseEntity.ok(chamadoService.listarChamados());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'GERENTE', 'SUPERVISOR', 'TECNICO')")
    public ResponseEntity<ChamadoResponse> buscarChamadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarChamadoPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'GERENTE', 'SUPERVISOR', 'TECNICO')")
    public ResponseEntity<ChamadoResponse> atualizarChamado(@PathVariable Long id, @RequestBody ChamadoRequest dadosChamado) {
        return  ResponseEntity.ok(chamadoService.atualizarChamado(id, dadosChamado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'GERENTE', 'SUPERVISOR')")
    public ResponseEntity<Void> deletarChamado(@PathVariable Long id) {
        chamadoService.deletarChamado(id);
        return ResponseEntity.noContent().build();
    }
}
