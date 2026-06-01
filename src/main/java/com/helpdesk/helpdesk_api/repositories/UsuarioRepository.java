package com.helpdesk.helpdesk_api.repositories;

import com.helpdesk.helpdesk_api.enums.Cargo;
import com.helpdesk.helpdesk_api.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCargo(Cargo cargo);

    @Query("""
            SELECT u FROM Usuario u
            WHERE :cargo IS NULL OR u.cargo = :cargo""")
    Page<Usuario> findByFiltros(@Param("cargo") Cargo cargo, Pageable pageable);
}
