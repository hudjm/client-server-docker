package com.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

    private T dados;
    private List<String> errors;

    public Response() {
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }

    public List<String> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<String>();
        }
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
