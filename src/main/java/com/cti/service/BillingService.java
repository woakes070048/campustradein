package com.cti.service;

import com.cti.common.annotation.billing.PayPal;
import com.cti.billing.CreditCardProcessor;
import com.cti.billing.TransactionLog;
import com.google.inject.Inject;

public class BillingService {
    private CreditCardProcessor processor;
    private TransactionLog transactionLog;

    @Inject
    private BillingService(@PayPal CreditCardProcessor processor, TransactionLog transactionLog) {
        this.processor = processor;
        this.transactionLog = transactionLog;
    }
}
