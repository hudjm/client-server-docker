package com.backend.services;

import com.backend.entity.Usuario;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.respository.UsuarioRepository;

/**
 *
 * @author hudson.magalhaes
 */

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    public Usuario save(Usuario user) {
        return userRepository.saveAndFlush(user);
    }

    public Usuario update(Usuario user) {
        return userRepository.save(user);
    }

    public Usuario find(String userName) {
        return userRepository.findOneByUsername(userName);
    }

    public Optional<Usuario> find(Long id) {
        return userRepository.findById(id);
    }
    
    public long count(){
        return userRepository.count();
    }
}
