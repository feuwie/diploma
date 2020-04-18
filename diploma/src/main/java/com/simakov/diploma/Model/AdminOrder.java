package com.simakov.diploma.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "AdminOrder")
public class AdminOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminorderId;
    private int orderId;
    private int productArticle;
    private int productId;
    private String productImg;
    private int productPrice;
    private String productTitle;
    private int productQuantity;
    private String userEmail;
}