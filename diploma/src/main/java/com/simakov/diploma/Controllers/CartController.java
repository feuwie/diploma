package com.simakov.diploma.Controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simakov.diploma.Model.Cart;
import com.simakov.diploma.Model.Payment;
import com.simakov.diploma.Model.Product;
import com.simakov.diploma.Model.User;
import com.simakov.diploma.Repository.CartRepository;
import com.simakov.diploma.Repository.ProductRepository;
import com.simakov.diploma.Response.Response;
import com.simakov.diploma.Utilities.Validator;
import com.simakov.diploma.Utilities.jwtUtil;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public ResponseEntity<Response> getCart(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN) throws IOException {
        Response resp = new Response();
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
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/addcart")
    public ResponseEntity<Response> addCart(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestBody int productId) throws IOException {
        Response resp = new Response();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                Product cartItem = productRepo.findByProductId(productId);
                cartRepo.dropId();
                cartRepo.autoIncrement();
                cartRepo.addId();
                Cart cart = new Cart();
                Date date = new Date();
                cart.setCartAdded(date);
                cart.setProductArticle(cartItem.getProductArticle());
                cart.setProductId(productId);
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
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/updcart")
    public ResponseEntity<Response> updateCart(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestParam(name = "cartid") String cartid, @RequestParam(name = "quantity") String quantity)
            throws IOException {
        Response resp = new Response();
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
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/delcart")
    public ResponseEntity<Response> deleteCart(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN,
            @RequestBody int cartid) throws IOException {
        Response resp = new Response();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                cartRepo.deleteByCartIdAndUserEmail(cartid, loggedUser.getEmail());
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
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/payment")
    public ResponseEntity<Response> paymentInfo(@RequestBody Payment chargeForm) {
        Token lover = chargeForm.getToken();
        Response resp = new Response();
        try {
            Stripe.apiKey = "sk_test_GKfX0jhismUAg2kdSNzIQaKX00IVS2UVEC";
            Map<String, Object> params = new HashMap<>();
            params.put("amount", chargeForm.getUserAmount());
            params.put("currency", "RUB");
            params.put("source", lover.getId());
            Charge.create(params);
            resp.setStatus("200");
            resp.setMessage("Order successed");
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }
}