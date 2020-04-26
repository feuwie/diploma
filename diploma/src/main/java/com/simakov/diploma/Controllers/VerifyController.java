package com.simakov.diploma.Controllers;

// import com.nexmo.client.verify.VerifyRequest;
import com.simakov.diploma.Model.Code;
import com.simakov.diploma.Model.User;
// import com.simakov.diploma.Model.User;
import com.simakov.diploma.Response.Response;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.exception.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.simakov.diploma.Constants.Constants;

@CrossOrigin(origins = "*")
@RestController
public class VerifyController {

    @PostMapping("/verifyphone")
    public ResponseEntity<Response> verifyPhone(@RequestBody User user) {
        Response resp = new Response();
        try {
            Twilio.init(Constants.ACCOUNT_SID, Constants.AUTH_TOKEN);
            Verification verification = Verification.creator(Constants.SERVICE_ID, user.getPhone(), "sms").create();
            resp.setStatus("200");
            resp.setMessage("PASS TO CODE");
            resp.setObject(verification.getServiceSid());
        } catch (ApiException err) {
            resp.setStatus(Integer.toString(err.getCode()));
            resp.setMessage(err.getMessage());
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/checkphone")
    public ResponseEntity<Response> checkPhone(@RequestBody Code code) {
        Response resp = new Response();
        try {
            Twilio.init(Constants.ACCOUNT_SID, Constants.AUTH_TOKEN);
            VerificationCheck verificationCheck = VerificationCheck.creator(code.getStr(), code.getCode())
                    .setTo(code.getPhone()).create();
            resp.setStatus("200");
            resp.setMessage("PASS TO CODE");
            resp.setObject(verificationCheck.getStatus());
        } catch (ApiException err) {
            resp.setStatus(Integer.toString(err.getCode()));
            resp.setMessage(err.getMessage());
        } catch (Exception e) {
            resp.setStatus("500");
            resp.setMessage(e.getMessage());
        }
        return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
    }
}