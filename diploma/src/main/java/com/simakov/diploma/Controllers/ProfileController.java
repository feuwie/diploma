package com.simakov.diploma.Controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.simakov.diploma.Model.AdminOrder;
import com.simakov.diploma.Model.User;
import com.simakov.diploma.Repository.AdminOrderRepository;
import com.simakov.diploma.Repository.CartRepository;
import com.simakov.diploma.Repository.UserRepository;
import com.simakov.diploma.Repository.WishlistRepository;
import com.simakov.diploma.Response.Response;
import com.simakov.diploma.Utilities.jwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class ProfileController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AdminOrderRepository aOrderRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private WishlistRepository wishRepo;

    @Autowired
    private jwtUtil jwtutil;

    @PostMapping("/profile")
    public ResponseEntity<Response> getProfile(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN) {
        // System.out.println(AUTH_TOKEN);
        // System.out.println(jwtutil.checkToken(AUTH_TOKEN));
        Response resp = new Response();
        if (jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                // System.out.println("HERE");
                User user = jwtutil.checkToken(AUTH_TOKEN);
                // HashMap<String, String> map = new HashMap<>();
                // // map.put("name", user.getUsername());
                // map.put("email", user.getEmail());
                // map.put("email", user.getEmail());
                // map.put("email", user.getEmail());
                resp.setStatus("200");
                resp.setMessage("PROFILE FOUND");
                // resp.setMap(map);
                resp.setAUTH_TOKEN(AUTH_TOKEN);
                resp.setObject(user);
            } catch (Exception e) {
                resp.setStatus("500");
                resp.setMessage(e.getMessage());
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            }
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getorders")
    public ResponseEntity<Response> getOrders(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN) {
        Response resp = new Response();
        if (jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User user = jwtutil.checkToken(AUTH_TOKEN);

                List<AdminOrder> usere = aOrderRepo.findByUserEmail(user.getEmail());
                resp.setStatus("200");
                resp.setMessage("PROFILE FOUND");
                resp.setAUTH_TOKEN(AUTH_TOKEN);
                resp.setOblist(usere);
            } catch (Exception e) {
                resp.setStatus("500");
                resp.setMessage(e.getMessage());
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            }
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/updprofilepinfo")
    public ResponseEntity<Response> updProfilePinfo(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestBody User user) {
        Response resp = new Response();
        if (jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                User usere = userRepo.findByPhoneAndUsertype(loggedUser.getPhone(), loggedUser.getUsertype());
                usere.setFullname(user.getFullname());
                usere.setDob(user.getDob());
                usere.setGender(user.getGender());
                userRepo.save(usere);
                resp.setStatus("200");
                resp.setMessage("PROFILE FOUND");
                resp.setAUTH_TOKEN(AUTH_TOKEN);
                resp.setObject(usere);
            } catch (Exception e) {
                resp.setStatus("500");
                resp.setMessage(e.getMessage());
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            }
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/delprofile")
    public ResponseEntity<Response> delProfile(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN) {
        Response resp = new Response();
        if (jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                userRepo.deleteByPhoneAndUsertype(loggedUser.getPhone(), loggedUser.getUsertype());
                cartRepo.deleteAllByUserEmail(loggedUser.getEmail());
                wishRepo.deleteAllByUserEmail(loggedUser.getEmail());
                // adminOrder.deleteAllByUserEmail(loggedUser.getEmail()); //Нужно ли
                // placeOrder.deleteAllByUserEmail(loggedUser.getEmail()); //Нужно ли
                resp.setStatus("200");
                resp.setMessage("PROFILE FOUND");
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
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/updphone")
    public ResponseEntity<Response> updPhone(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestBody User user) {
        Response resp = new Response();
        if (jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                User usere = userRepo.findByEmailAndUsertype(loggedUser.getEmail(), loggedUser.getUsertype());
                usere.setPhone(user.getPhone());
                userRepo.save(usere);

                String jwtToken = jwtutil.createToken(usere.getPhone(), "customer");

                resp.setStatus("200");
                resp.setMessage("PHONE UPD");
                resp.setAUTH_TOKEN(jwtToken);
                resp.setObject(usere);
            } catch (Exception e) {
                resp.setStatus("500");
                resp.setMessage(e.getMessage());
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            }
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

}