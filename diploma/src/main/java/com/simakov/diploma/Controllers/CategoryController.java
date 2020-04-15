package com.simakov.diploma.Controllers;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import com.simakov.diploma.Model.Category;
import com.simakov.diploma.Model.User;
import com.simakov.diploma.Repository.CategoryRepository;
import com.simakov.diploma.Repository.UserRepository;
import com.simakov.diploma.Response.categoryResp;
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
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    // public ResponseEntity<prodResp> getProducts(@RequestHeader(name =
    // "AUTH_TOKEN") String AUTH_TOKEN)
    @GetMapping("/category")
    public ResponseEntity<categoryResp> getCategory() throws IOException {
        categoryResp resp = new categoryResp();
        // if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) !=
        // null) {
        try {
            resp.setStatus("200");
            resp.setMessage("LIST_CAT");
            // resp.setAUTH_TOKEN(AUTH_TOKEN);
            resp.setOblist(categoryRepo.findAll());
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
            // resp.setAUTH_TOKEN(AUTH_TOKEN);
        }
        // } else {
        // resp.setStatus(ResponseCode.FAILURE_CODE);
        // resp.setMessage(ResponseCode.FAILURE_MESSAGE);
        // }
        // return new ResponseEntity<prodResp>(resp, HttpStatus.ACCEPTED);
        return new ResponseEntity<categoryResp>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/categoryroute")
    public ResponseEntity<categoryResp> getCategoryRoute(@RequestBody int route) throws IOException {
        categoryResp resp = new categoryResp();
        try {
            resp.setStatus("200");
            resp.setMessage("LIST_CAT");
            resp.setObject(categoryRepo.findById(route));
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<categoryResp>(resp, HttpStatus.ACCEPTED);
    }
}