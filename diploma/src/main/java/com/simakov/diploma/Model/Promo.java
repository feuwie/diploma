package com.simakov.diploma.Model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Promo")
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promoId;
    private String promoCode;
    private String promoType;
    private int promoValue;

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

    public int getPromoValue() {
        return promoValue;
    }

    public void setPromoValue(int promoValue) {
        this.promoValue = promoValue;
    }

    public Promo() {
        super();
    }

    public Promo(int promoId, String promoCode, String promoType, int promoValue) {
        super();
        this.promoId = promoId;
        this.promoCode = promoCode;
        this.promoType = promoType;
        this.promoValue = promoValue;
    }
}