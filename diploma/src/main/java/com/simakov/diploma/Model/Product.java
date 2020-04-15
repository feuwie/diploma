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
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productImg;
    private String productTitle;
    private int productPrice;
    private String productAbout;
    private int productArticle;
    private int productQuantity;
    private int parentId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductAbout() {
        return productAbout;
    }

    public void setProductAbout(String productAbout) {
        this.productAbout = productAbout;
    }

    public int getProductArticle() {
        return productArticle;
    }

    public void setProductArticle(int productArticle) {
        this.productArticle = productArticle;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Product() {
        super();
    }

    public Product(int productId, String productImg, String productTitle, int productPrice, String productAbout,
            int productArticle, int productQuantity, int parentId) {
        super();
        this.productId = productId;
        this.productImg = productImg;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productAbout = productAbout;
        this.productArticle = productArticle;
        this.productQuantity = productQuantity;
        this.parentId = parentId;
    }
}