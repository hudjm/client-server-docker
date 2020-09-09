/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.controller;

import com.backend.dto.AuthenticationDto;
import com.backend.dto.Response;
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
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService userService;

    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping
    public ResponseEntity<Response<TokenDto>> autenticacao(
            @Valid @RequestBody AuthenticationDto authenticationDto, BindingResult result)
            throws AuthenticationException {

        Response<TokenDto> response = new Response<>();
        if (result.hasErrors()) {
            logger.error("Erro validação: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Usuario user = this.userService.find(authenticationDto.getUsuario());
        if (user == null){
            logger.error("Usuário inválida");
            response.getErrors().add("Usuário inválido");
            return ResponseEntity.badRequest().body(response);
        }
        
        BCryptPasswordEncoder b =new BCryptPasswordEncoder();
        if (b.matches(authenticationDto.getSenha(), user.getPassword())){
        
            String token = Base64Util.encode(user.getUsername()+":"+authenticationDto.getSenha());
            response.setDados(new TokenDto(token));

            return ResponseEntity.ok(response);
        } else {
            logger.error("Senha inválida");
            response.getErrors().add("Senha não confere");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
