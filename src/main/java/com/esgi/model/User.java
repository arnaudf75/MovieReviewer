package com.esgi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by hideo on 02/04/16.
 */

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@Builder
@Entity
public class User {
    @GeneratedValue
    @Id
    @Column(name="iduser")
    private Long iduser;

    @Column(name = "name")
    private String name;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "pseudo", unique = true)
    private String pseudo;

    @Column(name = "password")
    private String password;

    @Column(name = "dateInscription")
    private Date dateInscription;

    @Column(name = "token")
    private String token;

    public User() {}

    public User(Long iduser, String firstName, String name, String pseudo, String token) {
        this.iduser = iduser;
        this.firstName = firstName;
        this.name = name;
        this.pseudo = pseudo;
        this.token = token;
    }

    public User(String name, String firstName, String pseudo, String password) {
        this.name = name;
        this.firstName = firstName;
        this.pseudo = pseudo;
        this.password = password;
        this.dateInscription = new Date();
        this.setToken();
    }

    public String getToken() {
        return token;
    }

    public void setToken() {
        this.token = UUID.randomUUID().toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
