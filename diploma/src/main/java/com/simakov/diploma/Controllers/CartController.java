package com.simakov.diploma.Controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.simakov.diploma.Model.Cart;
import com.simakov.diploma.Model.Product;
import com.simakov.diploma.Model.User;
import com.simakov.diploma.Repository.CartRepository;
import com.simakov.diploma.Repository.CategoryRepository;
import com.simakov.diploma.Repository.ProductRepository;
import com.simakov.diploma.Repository.UserRepository;
import com.simakov.diploma.Response.cartResp;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class CartController {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private jwtUtil jwtutil;

    @GetMapping("/cart")
    public ResponseEntity<cartResp> getCart(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN) throws IOException {
        cartResp resp = new cartResp();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                resp.setStatus("200");
                resp.setMessage("LIST_CART");
                resp.setAUTH_TOKEN(AUTH_TOKEN);
                resp.setOblist(cartRepo.findByUserEmail(loggedUser.getEmail()));
            } catch (Exception e) {
                resp.setStatus("500");
                resp.setMessage(e.getMessage());
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            }
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<cartResp>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/addcart")
    public ResponseEntity<serverResp> addCart(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestParam("productId") String productId) throws IOException {
        serverResp resp = new serverResp();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                Product cartItem = productRepo.findByProductId(Integer.parseInt(productId));
                cartRepo.dropId();
                cartRepo.autoIncrement();
                cartRepo.addId();
                Cart cart = new Cart();
                Date date = new Date();
                cart.setCartAdded(date);
                cart.setProductArticle(cartItem.getProductArticle());
                cart.setProductId(Integer.parseInt(productId));
                cart.setProductImg(cartItem.getProductImg());
                cart.setProductPrice(cartItem.getProductPrice());
                cart.setProductQuantity(1);
                cart.setProductTitle(cartItem.getProductTitle());
                cart.setUserEmail(loggedUser.getEmail());
                cartRepo.save(cart);
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
        return new ResponseEntity<serverResp>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/updcart")
    public ResponseEntity<cartResp> updateCart(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestParam(name = "cartid") String cartid, @RequestParam(name = "quantity") String quantity)
            throws IOException {

        cartResp resp = new cartResp();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                Cart updCart = cartRepo.findByCartIdAndUserEmail(Integer.parseInt(cartid), loggedUser.getEmail());
                updCart.setProductQuantity(Integer.parseInt(quantity));
                cartRepo.save(updCart);
                List<Cart> cartlist = cartRepo.findByUserEmail(loggedUser.getEmail());
                resp.setStatus("200");
                resp.setMessage("Cart updated");
                resp.setAUTH_TOKEN(AUTH_TOKEN);
                resp.setOblist(cartlist);
            } catch (Exception e) {
                resp.setStatus("500");
                resp.setMessage(e.getMessage());
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            }
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<cartResp>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/delcart")
    public ResponseEntity<cartResp> deleteCart(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestParam(name = "cartid") String cartid) throws IOException {
        cartResp resp = new cartResp();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                cartRepo.deleteByCartIdAndUserEmail(Integer.parseInt(cartid), loggedUser.getEmail());
                List<Cart> cartlist = cartRepo.findByUserEmail(loggedUser.getEmail());
                resp.setStatus("200");
                resp.setMessage("Cart deleted");
                resp.setAUTH_TOKEN(AUTH_TOKEN);
                resp.setOblist(cartlist);
            } catch (Exception e) {
                resp.setStatus("500");
                resp.setMessage(e.getMessage());
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            }
        } else {
            resp.setStatus("500");
            resp.setMessage("ERROR");
        }
        return new ResponseEntity<cartResp>(resp, HttpStatus.ACCEPTED);
    }
}