package com.simakov.diploma.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.simakov.diploma.Model.User;
import com.simakov.diploma.Repository.UserRepository;
import com.simakov.diploma.Response.Response;
import com.simakov.diploma.Utilities.Validator;
import com.simakov.diploma.Utilities.jwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    // @Autowired
    // private SessionRegistry sessionRegistry;

    @PostMapping("/registration")
    public ResponseEntity<Response> addUser(@Valid @RequestBody User user) {
        Response resp = new Response();
        try {
            if (Validator.isUserEmpty(user)) {
                resp.setStatus("400");
                resp.setMessage("BAD_REQUEST");
                // } else if (!Validator.isValidEmail(user.getEmail())) {
                // resp.setStatus("400");
                // resp.setMessage("INVALID_EMAIL");
            } else {
                resp.setStatus("200");
                resp.setMessage("REGISTERED");
                // String pass = user.getPassword();
                // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                // String hashedPassword = passwordEncoder.encode(pass);
                // user.setPassword(hashedPassword);
                User reg = userRepo.save(user);
                resp.setObject(reg);
            }
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    // @PostMapping("/login")
    // public ResponseEntity<Response> verifyUser(@Valid @RequestBody Map<String,
    // String> credential) {
    // String email = "";
    // String password = "";
    // if (credential.containsKey("email")) {
    // email = credential.get("email");
    // }
    // if (credential.containsKey("password")) {
    // password = credential.get("password");
    // }
    // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // User loggedUser = userRepo.findByEmailAndUsertype(email, "customer");
    // Response resp = new Response();
    // if (loggedUser != null) {
    // if (passwordEncoder.matches(password, loggedUser.getPassword())) {
    // String jwtToken = jwtutil.createToken(email, password, "customer");
    // resp.setStatus("200");
    // resp.setMessage("SUCCESS");
    // resp.setAUTH_TOKEN(jwtToken);
    // } else {
    // resp.setStatus("500");
    // resp.setMessage("ERROR");
    // }
    // } else {
    // resp.setStatus("500");
    // resp.setMessage("ERROR");
    // }
    // return new ResponseEntity<Response>(resp, HttpStatus.OK);
    // }
    @PostMapping("/login")
    public ResponseEntity<Response> verifyUser(@Valid @RequestBody User user, HttpSession userSession,
            HttpServletRequest request) {
        // String phone = "";
        // if (credential.containsKey("phone")) {
        // phone = credential.get("phone");
        // }
        // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User loggedUser = userRepo.findByPhoneAndUsertype(user.getPhone(), user.getUsertype());
        Response resp = new Response();
        if (loggedUser != null) {
            String jwtToken = jwtutil.createToken(user.getPhone(), user.getUsertype());

            resp.setStatus("200");
            resp.setMessage("SUCCESS");
            resp.setAUTH_TOKEN(jwtToken);

        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }

    @PostMapping("/loginmail")
    public ResponseEntity<Response> verifyMailUser(@Valid @RequestBody User user) {

        User loggedUser = userRepo.findByEmailAndUsertype(user.getEmail(), "customer");

        // String pass = "123";
        // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // String hashedPassword = passwordEncoder.encode(pass);

        Response resp = new Response();
        if (loggedUser != null) {
            // if (passwordEncoder.matches(user.getPassword(), loggedUser.getPassword())) {
            // String jwtToken = jwtutil.createMailToken(user.getEmail(),
            // user.getPassword(), "customer");
            String jwtToken = jwtutil.createToken(loggedUser.getPhone(), loggedUser.getUsertype());
            resp.setStatus("200");
            resp.setMessage("SUCCESS");
            resp.setAUTH_TOKEN(jwtToken);
            // } else {
            // resp.setStatus("500");
            // resp.setMessage("ERROR");
            // }
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }

    @GetMapping("/loginanon")
    public ResponseEntity<Response> loginAnon() {
        // // String phone = "";
        // // if (credential.containsKey("phone")) {
        // // phone = credential.get("phone");
        // // }
        // // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // User loggedUser = userRepo.findByPhoneAndUsertype(user.getPhone(),
        // user.getUsertype());

        UUID corrId = UUID.randomUUID();
        // System.out.println(corrId);

        String jwtToken = jwtutil.createAnonToken(corrId);
        // System.out.println(jwtToken);

        Response resp = new Response();
        // if (loggedUser != null) {
        // String jwtToken = jwtutil.createToken(user.getPhone(), user.getUsertype());

        resp.setStatus("200");
        resp.setMessage("SUCCESS");
        resp.setAUTH_TOKEN(jwtToken);
        resp.setObject(corrId);

        // } else {
        // resp.setStatus("500");
        // resp.setMessage("ERROR");
        // }
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }
}