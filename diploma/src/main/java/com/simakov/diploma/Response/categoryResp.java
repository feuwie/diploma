package com.simakov.diploma.Response;

import java.util.List;
import java.util.Optional;

import com.simakov.diploma.Model.Category;

public class categoryResp {
    private List<Category> oblist;
    private String status;
    private String message;
    private Optional<Category> object;

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

    public Optional<Category> getObject() {
        return object;
    }

    public void setObject(Optional<Category> object) {
        this.object = object;
    }

    public List<Category> getOblist() {
        return oblist;
    }

    public void setOblist(List<Category> oblist) {
        this.oblist = oblist;
    }

}
