package com.simakov.diploma.Response;

import java.util.List;

import com.simakov.diploma.Model.Product;

public class productResp {

    private List<Product> oblist;
    private String status;
    private String message;
    private Product object;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product getObject() {
        return object;
    }

    public void setObject(Product object) {
        this.object = object;
    }

    public List<Product> getOblist() {
        return oblist;
    }

    public void setOblist(List<Product> oblist) {
        this.oblist = oblist;
    }
}
