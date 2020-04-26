package com.simakov.diploma.Controllers;

import java.io.IOException;
import java.util.List;

import com.simakov.diploma.Model.Product;
import com.simakov.diploma.Model.User;
import com.simakov.diploma.Model.Wishlist;
import com.simakov.diploma.Repository.ProductRepository;
import com.simakov.diploma.Repository.WishlistRepository;
import com.simakov.diploma.Response.Response;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private jwtUtil jwtutil;

    @GetMapping("/wishlist")
    public ResponseEntity<Response> getWishlist(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN)
            throws IOException {
        Response resp = new Response();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                resp.setStatus("200");
                resp.setMessage("LIST_CART");
                resp.setAUTH_TOKEN(AUTH_TOKEN);
                resp.setOblist(wishlistRepo.findByUserEmail(loggedUser.getEmail()));
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

    // @PostMapping("/delwishlist")
    // public ResponseEntity<wishlistResp> delWishlist(@RequestHeader(name =
    // "AUTH_TOKEN") String AUTH_TOKEN,
    // @RequestBody Product product) throws IOException {
    // wishlistResp resp = new wishlistResp();
    // if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) !=
    // null) {
    // try {
    // User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
    // resp.setStatus("200");
    // resp.setMessage("LIST_CART");
    // resp.setAUTH_TOKEN(AUTH_TOKEN);
    // resp.setOblist(wishlistRepo.findByUserEmail(loggedUser.getEmail()));
    // } catch (Exception e) {
    // resp.setStatus("500");
    // resp.setMessage(e.getMessage());
    // resp.setAUTH_TOKEN(AUTH_TOKEN);
    // }
    // } else {
    // resp.setStatus("500");
    // resp.setMessage("ERROR");
    // }
    // return new ResponseEntity<wishlistResp>(resp, HttpStatus.ACCEPTED);
    // }

    @PostMapping("/delwishlist")
    public ResponseEntity<Response> delWishlist(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestBody int wishlistid) throws IOException {
        System.out.println(wishlistid);
        Response resp = new Response();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                wishlistRepo.deleteByWishlistIdAndUserEmail(wishlistid, loggedUser.getEmail());
                List<Wishlist> wishlist = wishlistRepo.findByUserEmail(loggedUser.getEmail());
                resp.setStatus("200");
                resp.setMessage("DEL_CART");
                resp.setAUTH_TOKEN(AUTH_TOKEN);
                resp.setOblist(wishlist);
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

    @PostMapping("/addwishlist")
    public ResponseEntity<Response> addWishlist(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestBody int productId) throws IOException {
        Response resp = new Response();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                Product wishItem = productRepo.findByProductId(productId);
                wishlistRepo.dropId();
                wishlistRepo.autoIncrement();
                wishlistRepo.addId();
                Wishlist wish = new Wishlist();
                wish.setUserEmail(loggedUser.getEmail());
                wish.setProductPrice(wishItem.getProductPrice());
                wish.setProductId(productId);
                wish.setProductTitle(wishItem.getProductTitle());
                wish.setProductArticle(wishItem.getProductArticle());
                wish.setProductImg(wishItem.getProductImg());
                wish.setProductQuantity(wishItem.getProductQuantity());
                wishlistRepo.save(wish);
                resp.setStatus("200");
                resp.setMessage("Wishlist updated");
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
}