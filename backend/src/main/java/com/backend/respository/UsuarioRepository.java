package com.backend.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.Usuario;

/**
 *
 * @author hudson.magalhaes
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findOneByUsername(String username);
}
