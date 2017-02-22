package com.bsc.payments.mvc;

import java.util.HashMap;
import java.util.Map;

import com.bsc.payments.utils.Currency;
import com.bsc.payments.utils.Payment;

/**
 * Class in charge of keeping payments balance
 */
public class PaymentsModel {

    /** Balance of payments */
    private Map<Currency, Double> balance = new HashMap<Currency, Double>();

    /**
     * Default constructor
     */
    public PaymentsModel() {
    }

    /**
     * Add the payment to the balance.
     * 
     * @param p payment to be added
     */
    protected void add(Payment p) {
        balance.merge(p.getCurrency(), p.getAmount(), Double::sum);
    }

    /**
     * Gets the payments balance
     * 
     * @return balance
     */
    protected Map<Currency, Double> getBalance() {
        return balance;
    }

}
