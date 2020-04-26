package com.simakov.diploma.Controllers;

import java.io.IOException;

import com.simakov.diploma.Model.Product;
import com.simakov.diploma.Repository.ProductRepository;
import com.simakov.diploma.Response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @PostMapping("/{parent}")
    public ResponseEntity<Response> getProductParent(@RequestBody Product product) {
        Response resp = new Response();
        try {
            resp.setStatus("200");
            resp.setMessage("LIST_PROD");
            resp.setOblist(productRepo.findByParentId(product.getParentId()));
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{title}")
    public ResponseEntity<Response> getProductTitle(@RequestBody Product product) {
        Response resp = new Response();
        try {
            resp.setStatus("200");
            resp.setMessage("LIST_PROD");
            resp.setOblist(productRepo.findByProductTitleLike("%" + product.getProductTitle() + "%"));
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{article}")
    public ResponseEntity<Response> getProductArticle(@RequestBody Product product) {
        Response resp = new Response();
        try {
            resp.setStatus("200");
            resp.setMessage("PROD");
            resp.setObject(productRepo.findByProductArticle(product.getProductArticle()));
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getproducts")
    public ResponseEntity<Response> getProducts() throws IOException {

        Response resp = new Response();
        try {
            resp.setStatus("200");
            resp.setMessage("LIST ITEMS");
            resp.setOblist(productRepo.findAll());
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }
}