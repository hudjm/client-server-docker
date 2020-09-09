/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.controller;

import com.backend.dto.SignUpDto;
import com.backend.dto.Response;
import com.backend.dto.RetornoCadastro;
import com.backend.dto.TokenDto;
import com.backend.entity.Usuario;
import com.backend.services.UsuarioService;
import com.backend.services.util.Base64Util;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hudson.magalhaes
 */
@RestController
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private UsuarioService userService;

    public static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @PostMapping
    public ResponseEntity<Response<RetornoCadastro>> novoUsuario(
            @Valid @RequestBody SignUpDto dto, BindingResult result)
            throws AuthenticationException {

        Response<RetornoCadastro> response = new Response<>();
        if (result.hasErrors()) {
            logger.error("Erro validação: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Usuario user = this.userService.find(dto.getUsername());
        if (user != null){
            logger.error("Usuário já cadastrado");
            response.getErrors().add("Usuário já cadastrado");
            return ResponseEntity.badRequest().body(response);
        }
        
       
        user = new Usuario(dto.getUsername(), new BCryptPasswordEncoder().encode(dto.getPassword()), dto.getNome());
        userService.save(user);
        
        response.setDados(new RetornoCadastro("Operação realizada com sucesso!", user.getId()));
        return ResponseEntity.ok(response);
    }
}
