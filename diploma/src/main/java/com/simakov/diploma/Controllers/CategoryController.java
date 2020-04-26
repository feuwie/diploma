package com.simakov.diploma.Controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.simakov.diploma.Constants.Constants;
import com.simakov.diploma.Model.User;
import com.simakov.diploma.Repository.CategoryRepository;
import com.simakov.diploma.Response.Response;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.IncomingPhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.chat.v1.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.type.PhoneNumber;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    // public ResponseEntity<prodResp> getProducts(@RequestHeader(name =
    // "AUTH_TOKEN") String AUTH_TOKEN)
    @GetMapping("/category")
    public ResponseEntity<Response> getCategory(HttpSession session) throws IOException {
        Response resp = new Response();
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
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/categoryroute")
    public ResponseEntity<Response> getCategoryRoute(@RequestBody int route) throws IOException {
        Response resp = new Response();
        try {
            resp.setStatus("200");
            resp.setMessage("LIST_CAT");
            resp.setObject(categoryRepo.findById(route));
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }
}