package com.esgi.model;

import javax.persistence.*;

/**
 * Created by Arnaud Flaesch on 13/04/2016.
 */
@Entity
@IdClass(ReviewEntityPK.class)
@Table(name = "reviews", schema = "moviereviewer")
public class ReviewEntity {

    private int idmovie;
    private float rating;
    private int iduser;

    @Id
    @Column(name = "iduser")
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Id
    @Column(name = "idmovie")
    public int getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(int idmovie) {
        this.idmovie = idmovie;
    }

    @Basic
    @Column(name = "rating")
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
