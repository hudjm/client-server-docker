package com.backend.dto;

public class RetornoCadastro {

    private String mensagem;
    private long id;

    public RetornoCadastro() {
    }

    public RetornoCadastro(String mensagem, long id) {
        this.mensagem = mensagem;
        this.id = id;
    }

    
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
}
