package com.simakov.diploma.Controllers;

import java.util.Map;

import javax.validation.Valid;

import com.simakov.diploma.Model.User;
import com.simakov.diploma.Repository.UserRepository;
import com.simakov.diploma.Response.serverResp;
import com.simakov.diploma.Utilities.Validator;
import com.simakov.diploma.Utilities.jwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private jwtUtil jwtutil;

    @PostMapping("/registration")
    public ResponseEntity<serverResp> addUser(@Valid @RequestBody User user) {
        serverResp resp = new serverResp();
        try {
            if (Validator.isUserEmpty(user)) {
                resp.setStatus("400");
                resp.setMessage("BAD_REQUEST");
            } else if (!Validator.isValidEmail(user.getEmail())) {
                resp.setStatus("400");
                resp.setMessage("INVALID_EMAIL");
            } else {
                resp.setStatus("200");
                resp.setMessage("REGISTERED");
                User reg = userRepo.save(user);
                resp.setObject(reg);
            }
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<serverResp>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<serverResp> verifyUser(@Valid @RequestBody Map<String, String> credential) {
        String email = "";
        String password = "";
        if (credential.containsKey("email")) {
            email = credential.get("email");
        }
        if (credential.containsKey("password")) {
            password = credential.get("password");
        }
        User loggedUser = userRepo.findByEmailAndPasswordAndUsertype(email, password, "customer");
        serverResp resp = new serverResp();
        if (loggedUser != null) {
            String jwtToken = jwtutil.createToken(email, password, "customer");
            resp.setStatus("200");
            resp.setMessage("SUCCESS");
            resp.setAUTH_TOKEN(jwtToken);
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<serverResp>(resp, HttpStatus.OK);
    }
}