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
@Table(name = "Wishlist")
public class Wishlist implements Serializable {

    private static final long serialVersionUID = 4049687597028261161L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlistId;

    private String userEmail;
    private int productId;
    private String productImg;
    private String productTitle;
    private int productPrice;
    private int productArticle;

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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