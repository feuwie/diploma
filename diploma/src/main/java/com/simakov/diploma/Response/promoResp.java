package com.simakov.diploma.Response;

import java.util.List;

import com.simakov.diploma.Model.Promo;

public class promoResp {

    private List<Promo> oblist;
    private String status;
    private String message;

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

    public List<Promo> getOblist() {
        return oblist;
    }

    public void setOblist(List<Promo> oblist) {
        this.oblist = oblist;
    }
}
