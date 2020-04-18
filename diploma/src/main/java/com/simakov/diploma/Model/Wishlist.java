package com.simakov.diploma.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlistId;
    private String userEmail;
    private int productId;
    private String productImg;
    private String productTitle;
    private int productPrice;
    private int productArticle;
    private int productQuantity;
}