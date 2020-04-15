package com.simakov.diploma.Response;

import java.util.List;

import com.simakov.diploma.Model.Wishlist;

public class wishlistResp {
    private List<Wishlist> oblist;
    private String status;
    private String message;
    private String AUTH_TOKEN;

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

    public String getAUTH_TOKEN() {
        return AUTH_TOKEN;
    }

    public void setAUTH_TOKEN(String aUTH_TOKEN) {
        AUTH_TOKEN = aUTH_TOKEN;
    }

    public List<Wishlist> getOblist() {
        return oblist;
    }

    public void setOblist(List<Wishlist> oblist) {
        this.oblist = oblist;
    }

}
