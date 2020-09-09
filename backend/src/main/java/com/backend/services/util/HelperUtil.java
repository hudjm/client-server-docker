/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.services.util;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Hudson
 */
public class HelperUtil {
    
    
    public static boolean validarCodigoBarras(String codigoBarras) {
        boolean apenasNumeros = true;

        if (codigoBarras.isEmpty()) {
            return false;
        }

        // Passa por todos os caracter checando se eh apenas numero
        for (char digito : codigoBarras.toCharArray()) {
            // Checa se eh um numero
            if (!Character.isDigit(digito)) {
                apenasNumeros = false;
                break;
            }
        }
        // Checa se o que foi passado por parametro eh apenas numero
        if (apenasNumeros) {
            // Salva o ultimo digito do codigo (digito verificador)
            int digito = Integer.parseInt("" + codigoBarras.charAt(codigoBarras.length() - 1));
            // Pega todos os digetos mas sem o digito verificador
            String codigoSemDigitoVerificador = codigoBarras.substring(0, codigoBarras.length() - 1);
            // Vareavel para armazenar a soma dos digitos
            int soma = 0;

            // Checa a quantidade de digito
            if ((codigoBarras.length() == 13) || (codigoBarras.length() == 17)) {

                // Passa por todos os digitos do codigo sem o digito verificador
                for (int i = 0; i < codigoSemDigitoVerificador.length(); i++) {
                    // Checa se i eh par
                    if (((i + 1) % 2) == 0) {
                        // Mutiplica por 3 e depois soma
                        soma += Integer.parseInt("" + codigoSemDigitoVerificador.charAt(i)) * 3;
                    } else {
                        // Soma os digitos
                        soma += Integer.parseInt("" + codigoSemDigitoVerificador.charAt(i));
                    }
                }
            } else if ((codigoBarras.length() == 8) || (codigoBarras.length() == 12) || (codigoBarras.length() == 14) || (codigoBarras.length() == 18)) {
                // Passa por todos os digitos do codigo sem o digito verificador
                for (int i = 0; i < codigoSemDigitoVerificador.length(); i++) {

                    // Checa se i eh par
                    if (((i + 1) % 2) == 0) {
                        // Soma os digitos
                        soma += Integer.parseInt("" + codigoSemDigitoVerificador.charAt(i));
                    } else {
                        // Mutiplica por 3 e depois soma
                        soma += Integer.parseInt("" + codigoSemDigitoVerificador.charAt(i)) * 3;
                    }
                }
                // Retorna falso caso nao seja um codigo de barra do tamanho valido pelo GS1
            } else {
                return false;
            }
            int somaMultipla = soma;

            // Entra no while enquanto a soma nao for multiplo de 10
            while ((somaMultipla % 10) != 0) {
                somaMultipla++;
            }
            // Subtraia soma por um mÃºltiplo de 10 superior mais prÃ³ximo a ele
            // Depois checa se o resultado da subtracao eh igual ao digito passado por paramento
            int vlr = (soma - somaMultipla);
            if (vlr < 0) {
                vlr = vlr * -1;
            }

            return vlr == digito;

        } else {
            return false;
        }
    }
    
     public static String limparPontos(String text) {
        String s = text;
        
        if (text == null || text.isEmpty())
            return "";

        s = s.replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "").
                replaceAll(",", "").replaceAll("/", "").replaceAll(" ", "");
        return s.trim();
    }

   
    public static String limparAcentos(String str) {
        try {
            if (str == null)
                return null;

            String s = str.replace("ç", "c").replace("Ç", "Ç").
                    replace("ã", "a").replace("Ã", "A").
                    replace("õ", "o").replace("Õ", "O").
                    replace("á", "a").replace("Á", "A").
                    replace("é", "e").replace("É", "E").
                    replace("í", "i").replace("í", "i").
                    replace("Ó", "o").replace("ó", "O").
                    replace("Ú", "u").replace("ú", "U").
                    replace("Â", "a").replace("Â", "A").
                    replace("ê", "e").replace("ê", "E").
                    replace("à", "a").replace("À", "A").
                    replace("²", "2").replace("³", "3");
            
            String normalizado = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            return normalizado.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }
    public static String formatToCpfCnpj(String cpfCnpj) {
        String s = "";
        if (cpfCnpj == null || cpfCnpj.isEmpty())
            return "";
        
        if (cpfCnpj.length() > 11) {
            for (int i = 0; i < cpfCnpj.length(); i++) {
                s += cpfCnpj.charAt(i);
                if (i == 1 || i == 4) {
                    s += ".";
                }
                if (i == 7) {
                    s += "/";
                }
                if (i == 11) {
                    s += "-";
                }
            }
        } else {
            for (int i = 0; i < cpfCnpj.length(); i++) {
                s += cpfCnpj.charAt(i);
                if (i == 2 || i == 5) {
                    s += ".";
                }
                if (i == 8) {
                    s += "-";
                }
            }
        }
        return s;
    }
    
    public static String formatToTelefone(String telefone) {
        StringBuilder resultado = new StringBuilder();
        String mascara = "(##)####-#####";
        if (telefone == null)
            return "";
        
        if (telefone.isEmpty() || telefone.length() < 8)
            return telefone;
        
        if (telefone.length() > 10) 
            mascara = "(##)#####-####";
        
        
        
        String alphaAndDigits = somenteDigitos(telefone);
        int i = 0;
        int quant = 0;
        while (i<mascara.length()) {
            if (quant < alphaAndDigits.length()) {
                if ("#".equals(mascara.substring(i,i+1))) {
                    resultado.append(alphaAndDigits.substring(quant,quant+1));
                    quant++;
                } else {
                   resultado.append(mascara.substring(i,i+1));
                }
            }
        i++;    
        }
        
        return resultado.toString();
    }
    
     public static String somenteDigitos(String text) {
        if (text == null)
            return null;
        
        return text.replaceAll("[^0-9]", "");       
    }
    

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean ValidarCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null) {
            return false;
        }

        if (cpfCnpj.length() == 11) {
            return ValidarCPF(cpfCnpj);
        } else {
            return ValidarCNPJ(cpfCnpj);
        }
    }

    public static boolean ValidarCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public static boolean ValidarCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) {
            return false;
        }

        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public static String formatDate(Date data) {
        return formatDate(data, "dd/MM/yyyy");
    }
    
    public static String formatDateHora(Date data) {
        return formatDate(data, "dd/MM/yyyy HH:mm:ss");
    }

    public static String formatDate(Date data, String mascara) {
        if (data == null)
            return "";
        SimpleDateFormat formatador = new SimpleDateFormat(mascara, new Locale("pt", "BR"));
        return formatador.format(data);
    }

    public static String formatNumber(BigDecimal valor) {
        return formatNumber(valor,2);
    }
    
    public static String formatNumber(BigDecimal valor, int casasdecimais) {
        if (valor == null) {
            valor = BigDecimal.ZERO;
        }
        String mascara = "##,###,###,##0.00";
        if (casasdecimais > 2){
            for (int i = 0; i < (casasdecimais - 2); i++)
                mascara += "0";
        }
        
        DecimalFormat formatoDois = new DecimalFormat(mascara, new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(casasdecimais);
        formatoDois.setParseBigDecimal(true);
        String ret =  (formatoDois.format(valor));
        return ret;
    }

    
}