package com.backend.entity;


import java.io.Serializable;
import javax.persistence.*;


/**
 *
 * @author hudson.magalhaes
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    
    
    @Column(unique = true, length = 50)
    private String username;
    
    @Column(length = 255)
    private String password;
    
    @Column(length = 50)
    private String role;
    
    @Column(length = 150)
    private String nome;

    public Usuario() {

    }

    public Usuario(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.nome = fullName;
        this.role = "USER";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    

 

}
