package com.simakov.diploma.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 4049687597028261161L;

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
    private int productArticle; // change

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCartAdded() {
        return cartAdded;
    }

    public void setCartAdded(Date cartAdded) {
        this.cartAdded = cartAdded;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getProductArticle() {
        return productArticle;
    }

    public void setProductArticle(int productArticle) {
        this.productArticle = productArticle;
    }
}