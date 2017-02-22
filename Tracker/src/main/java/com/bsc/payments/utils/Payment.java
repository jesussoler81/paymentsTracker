package com.bsc.payments.utils;

/**
 * Class to represent a payment. This class is not really necessary but in case
 * a payment will have more properties we are ready to go. And we have a
 * centralised place to validate a payment.
 */
public class Payment {

    /** The currency of the payment */
    private Currency currency;
    /** The amount of the payment */
    private Double amount;

    /**
     * Constructs a payment with the specify currency and amount.
     * 
     * @param currency
     * @param amount
     */
    public Payment(Currency currency, Double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    /**
     * Payment´s currency
     * 
     * @return
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Payment´s amount
     * 
     * @return
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Creates a payment from a currency and amount represented as a String.
     * This is similar to what a constructor would do except that there is a
     * validation of the strings and in case it is not possible to convert them
     * to currency and amount, null is returned.
     * 
     * @param curr string representing the currency
     * @param amnt string representing the amount
     * @return A Payment, or Null in case of error converting the parameters.
     */
    public static Payment createPayment(String curr, String amnt) {
        try {
            Currency currency = Currency.valueOf(curr);
            Double amount = Double.valueOf(amnt);
            return new Payment(currency, amount);
        } catch (Exception e) {
            System.err.println("Couldn't convert '" + curr + "' to a currency and '" + amnt + "' to an amount.");
        }
        return null;
    }

}
