package com.backend.entity;


import com.backend.enums.EnumSexo.ENSexo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 *
 * @author hudson.magalhaes
 */
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    
    @Basic(optional = false)
    @Column(length = 150)
    private String nome;
    
    @Basic(optional = false)
    @Column(length = 20)
    private String cpf;
    
    @Column(length = 50)
    private String email;
    
    @Column(length = 50)
    private String naturalidade;
    
    @Column(length = 50)
    private String nacionalidade;
    
    @Basic(optional = false)
    @Column(name = "sexo")
    private ENSexo sexo;
    
    @Basic(optional = false)
    @Column()
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;

    public Pessoa() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public ENSexo getSexo() {
        return sexo;
    }

    public void setSexo(ENSexo sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    
    

 

}
