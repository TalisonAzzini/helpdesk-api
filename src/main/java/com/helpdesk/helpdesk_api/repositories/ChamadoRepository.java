package com.helpdesk.helpdesk_api.repositories;

import com.helpdesk.helpdesk_api.enums.Prioridade;
import com.helpdesk.helpdesk_api.enums.Status;
import com.helpdesk.helpdesk_api.models.Chamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    @Query("""
            SELECT c FROM Chamado c
            WHERE (:status IS NULL OR c.status = :status)
            AND (:prioridade IS NULL OR c.prioridade = :prioridade)""")
    Page<Chamado> findByFiltros(@Param("status") Status status, @Param("prioridade") Prioridade prioridade, Pageable pageable);
}
