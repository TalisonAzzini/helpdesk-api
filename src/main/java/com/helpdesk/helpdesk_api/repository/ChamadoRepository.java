package com.helpdesk.helpdesk_api.repository;

import com.helpdesk.helpdesk_api.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
}
