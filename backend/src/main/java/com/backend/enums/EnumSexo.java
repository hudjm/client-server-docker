/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.enums;

/**
 *
 * @author hudson.magalhaes
 */
public class EnumSexo {
    
    public enum ENSexo{
        MASCULINO(0, "Masculino"),
        FEMININO(1, "Feminino");
        
        private final int valor;
        private final String descricao;
        
        ENSexo(int tip, String desc) {
            this.valor = tip;
            descricao = desc;
        }

        @Override
        public String toString() {
            return descricao;
        }
        
        public int getValor(){
            return this.valor;
        }
    }
}
