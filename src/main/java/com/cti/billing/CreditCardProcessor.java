package com.cti.billing;

/**
 * Created by ifeify on 5/1/16.
 */
public interface CreditCardProcessor {
    public ChargeResult charge(CreditCard creditCard, double amount);
}
