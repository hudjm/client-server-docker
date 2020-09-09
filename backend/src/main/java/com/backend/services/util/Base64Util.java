/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.services.util;

import java.util.Base64;

/**
 *
 * @author hudson.magalhaes
 */
public class Base64Util {
 
    public static String encode(String s){
        if (s == null)
            return "";
        
        byte[] encodedBytes = Base64.getEncoder().encode(s.getBytes());
        String ret = new String(encodedBytes);
        return ret;
    }
}
