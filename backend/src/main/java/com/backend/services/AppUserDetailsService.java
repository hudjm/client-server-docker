package com.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.entity.Usuario;
import com.backend.security.BasicUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author hudson.magalhaes
 */

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userService.count() <= 0){
            Usuario user = new Usuario("teste", new BCryptPasswordEncoder().encode("teste"), "teste");
            userService.save(user);
        }
        
        
        Usuario user = userService.find(username);
        if (user == null)
            return null;
        List<GrantedAuthority> authorities= Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
        return new BasicUser(user.getId(), user.getUsername(), user.getPassword(), authorities);
    }

}
