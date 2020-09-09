package com.backend.services;

import com.backend.entity.Pessoa;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.respository.PessoaRepository;
import java.util.List;

/**
 *
 * @author hudson.magalhaes
 */

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.saveAndFlush(pessoa);
    }

    public Pessoa update(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }
    
    public void delete(Pessoa pessoa) {
        pessoaRepository.delete(pessoa);
    }
    
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> findByName(String nome) {
        return pessoaRepository.findOneByNome(nome);
    }
    
    public Optional<Pessoa> findByCpf(String cpf) {
        return pessoaRepository.findOneByCpf(cpf);
    }

    public Optional<Pessoa> find(Long id) {
        return pessoaRepository.findById(id);
    }
    
    public long count(){
        return pessoaRepository.count();
    }
}
