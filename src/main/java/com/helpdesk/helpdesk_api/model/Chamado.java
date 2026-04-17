package com.helpdesk.helpdesk_api.model;

import com.helpdesk.helpdesk_api.enums.Prioridade;
import com.helpdesk.helpdesk_api.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chamado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;  //BAIXA, MEDIA ou ALTA

    @Enumerated(EnumType.STRING)
    private Status status;  //ABERTO, EM_ANDAMENTO ou FECHADO

    private LocalDateTime dataCriado;
    private LocalDateTime dataFechado;

    @ManyToOne
    private Usuario solicitante;

    @ManyToOne
    private Usuario tecnico;
}