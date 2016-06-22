package com.cti.billing;

import com.google.inject.name.Named;

import javax.inject.Inject;

/**
 * Created by ifeify on 5/1/16.
 */
public class PaypalCreditCardProcessor implements CreditCardProcessor {
    private final String apiKey;
    
    @Inject
    public PaypalCreditCardProcessor(@Named("PayPal API Key")String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public ChargeResult charge(CreditCard creditCard, double amount) {
        return null;
    }
}
