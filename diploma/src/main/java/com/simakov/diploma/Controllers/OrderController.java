package com.simakov.diploma.Controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.simakov.diploma.Model.AdminOrder;
import com.simakov.diploma.Model.Cart;
import com.simakov.diploma.Model.PlaceOrder;
import com.simakov.diploma.Model.Product;
import com.simakov.diploma.Model.User;
import com.simakov.diploma.Repository.AdminOrderRepository;
import com.simakov.diploma.Repository.CartRepository;
import com.simakov.diploma.Repository.OrderRepository;
import com.simakov.diploma.Repository.ProductRepository;
import com.simakov.diploma.Response.Response;
import com.simakov.diploma.Utilities.Validator;
import com.simakov.diploma.Utilities.jwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private AdminOrderRepository adminOrderRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private jwtUtil jwtutil;

    @GetMapping("/makeorder")
    public ResponseEntity<Response> makeOrder(@RequestHeader(name = "AUTH_TOKEN") String AUTH_TOKEN)
            throws IOException {

        Response resp = new Response();
        if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtutil.checkToken(AUTH_TOKEN) != null) {
            try {
                User loggedUser = jwtutil.checkToken(AUTH_TOKEN);
                PlaceOrder order = new PlaceOrder();
                order.setUserEmail(loggedUser.getEmail());
                Date date = new Date();
                order.setOrderDate(date);
                order.setOrderStatus("PENDING");
                int total = 0;
                List<Cart> cartlist = cartRepo.findAllByUserEmail(loggedUser.getEmail());
                for (Cart cart : cartlist) {
                    total = total + (cart.getProductQuantity() * cart.getProductPrice());
                    Product product = productRepo.findByProductArticle(cart.getProductArticle());
                    product.setProductQuantity(product.getProductQuantity() - cart.getProductQuantity());
                }

                order.setOrderCost(total);
                PlaceOrder res = orderRepo.save(order);

                orderRepo.dropId();
                orderRepo.autoIncrement();
                orderRepo.addId();

                cartlist.forEach((cart) -> {
                    AdminOrder ord = new AdminOrder();
                    ord.setOrderId(res.getOrderId());
                    ord.setProductArticle(cart.getProductArticle());
                    ord.setProductId(cart.getProductId());
                    ord.setProductImg(cart.getProductImg());
                    ord.setProductPrice(cart.getProductPrice());
                    ord.setProductQuantity(cart.getProductQuantity());
                    ord.setProductTitle(cart.getProductTitle());
                    ord.setUserEmail(cart.getUserEmail());
                    adminOrderRepo.save(ord);
                });

                adminOrderRepo.dropId();
                adminOrderRepo.autoIncrement();
                adminOrderRepo.addId();

                cartRepo.deleteAllByUserEmail(loggedUser.getEmail());

                resp.setStatus("200");
                resp.setMessage("Success added");
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            } catch (Exception e) {
                resp.setStatus("500");
                resp.setMessage(e.getMessage());
                resp.setAUTH_TOKEN(AUTH_TOKEN);
            }
        } else {
            resp.setStatus("500");
            resp.setMessage("Error");
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    
}