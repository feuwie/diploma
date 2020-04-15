package com.simakov.diploma.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.simakov.diploma.Model.User;
import com.simakov.diploma.Model.Category;
import com.simakov.diploma.Repository.CategoryRepository;
import com.simakov.diploma.Repository.UserRepository;
import com.simakov.diploma.Response.categoryResp;
import com.simakov.diploma.Response.profileResp;
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
public class ProfileController {

    @Autowired
    private jwtUtil jwtutil;

    @PostMapping("/profile")
    public ResponseEntity<profileResp> getProfile(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN) {
        profileResp resp = new profileResp();
        if (jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User user = jwtutil.checkToken(AUTH_TOKEN);
                HashMap<String, String> map = new HashMap<>();
                map.put("name", user.getUsername());
                map.put("email", user.getEmail());
                resp.setStatus("200");
                resp.setMessage("PROFILE FOUND");
                resp.setMap(map);
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            } catch (Exception e) {
                resp.setStatus("500");
                resp.setMessage(e.getMessage());
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            }
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<profileResp>(resp, HttpStatus.ACCEPTED);
    }
}