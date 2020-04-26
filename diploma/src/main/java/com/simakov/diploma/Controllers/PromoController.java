package com.simakov.diploma.Controllers;

import java.io.IOException;

import com.simakov.diploma.Repository.PromoRepository;
import com.simakov.diploma.Response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class PromoController {

    @Autowired
    private PromoRepository promoRepo;

    @GetMapping("/getpromo")
    public ResponseEntity<Response> getProducts() throws IOException {
        Response resp = new Response();
        try {
            resp.setStatus("200");
            resp.setMessage("LIST ITEMS");
            resp.setOblist(promoRepo.findAll());
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }
}