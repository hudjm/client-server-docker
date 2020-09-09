package com.backend.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class AuthenticationDto {

    @NotEmpty(message = "Usuário não pode ser vazio.")
    private String usuario;
    
    @NotEmpty(message = "Senha não pode ser vazia.")
    private String senha;

    public AuthenticationDto() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    
   
    
}
