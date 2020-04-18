package com.simakov.diploma.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @Column(nullable = true)
    private int orderId;

    private String userEmail;
    private Date cartAdded;
    private int productQuantity;
    private int productPrice;
    private int productId;
    private String productImg;
    private String productTitle;
    private int productArticle;
}