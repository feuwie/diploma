package com.simakov.diploma.Model;

import com.stripe.model.Token;

import lombok.Data;

@Data
public class Payment {
    private String userEmail;
    private int userAmount;
    private Token token;
}