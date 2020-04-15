package com.simakov.diploma.Controllers;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import com.simakov.diploma.Model.Product;
import com.simakov.diploma.Model.User;
import com.simakov.diploma.Repository.CategoryRepository;
import com.simakov.diploma.Repository.ProductRepository;
import com.simakov.diploma.Repository.UserRepository;
import com.simakov.diploma.Response.categoryResp;
import com.simakov.diploma.Response.productResp;
import com.simakov.diploma.Response.serverResp;
import com.simakov.diploma.Utilities.Validator;
import com.simakov.diploma.Utilities.jwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @PostMapping("/{parent}")
    public ResponseEntity<productResp> getProductParent(@RequestBody Product product) {
        productResp resp = new productResp();
        try {
            resp.setStatus("200");
            resp.setMessage("LIST_PROD");
            resp.setOblist(productRepo.findByParentId(product.getParentId()));
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<productResp>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{title}")
    public ResponseEntity<productResp> getProductTitle(@RequestBody Product product) {
        productResp resp = new productResp();
        try {
            resp.setStatus("200");
            resp.setMessage("LIST_PROD");
            resp.setOblist(productRepo.findByProductTitleLike("%" + product.getProductTitle() + "%"));
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<productResp>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{article}")
    public ResponseEntity<productResp> getProductArticle(@RequestBody Product product) {
        productResp resp = new productResp();
        try {
            resp.setStatus("200");
            resp.setMessage("PROD");
            resp.setObject(productRepo.findByProductArticle(product.getProductArticle()));
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<productResp>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getproducts")
    public ResponseEntity<productResp> getProducts() throws IOException {

        productResp resp = new productResp();
        try {
            resp.setStatus("200");
            resp.setMessage("LIST ITEMS");
            resp.setOblist(productRepo.findAll());
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<productResp>(resp, HttpStatus.ACCEPTED);
    }
}