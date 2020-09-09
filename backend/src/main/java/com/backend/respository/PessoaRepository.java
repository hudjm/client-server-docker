package com.backend.respository;

import com.backend.entity.Pessoa;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 *
 * @author hudson.magalhaes
 */

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    public Optional<Pessoa> findOneByNome(String nome);
    public Optional<Pessoa> findOneByCpf(String cpf);
}
