package com.backend.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class SignUpDto {

    @NotEmpty(message = "Usuário não pode ser vazio.")
    private String username;
    
    @NotEmpty(message = "Senha não pode ser vazia.")
    private String password;
    
    @NotEmpty(message = "Nome não pode ser vazio.")
    private String nome;

    public SignUpDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
   
    
}
