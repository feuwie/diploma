package com.simakov.diploma.Model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Category")
public class Category implements Serializable {

    private static final long serialVersionUID = -7446162716367847201L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_id;
    private String category_title;
    private String category_img;

    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

    public String getCategoryTitle() {
        return category_title;
    }

    public void setCategoryTitle(String category_title) {
        this.category_title = category_title;
    }

    public String getCategoryImg() {
        return category_img;
    }

    public void setCategoryImg(String category_img) {
        this.category_img = category_img;
    }

    public Category() {
        super();
    }

    public Category(int category_id, String category_title, String category_img) {
        super();
        this.category_id = category_id;
        this.category_title = category_title;
        this.category_img = category_img;
    }
}