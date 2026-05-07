package com.helpdesk.helpdesk_api.repositories;

import com.helpdesk.helpdesk_api.enums.Cargo;
import com.helpdesk.helpdesk_api.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCargo(Cargo cargo);
}
