package com.bsc.payments.mvc;

import java.util.Map;

import com.bsc.payments.utils.Converter;
import com.bsc.payments.utils.Currency;

/**
 * Class in charge of displaying the payments balance
 */
public class PaymentsView {

    /**
     * Auxiliary method to print the USD conversion of a payment.
     * 
     * @param currency of the payment
     * @param amount of the payment
     * @return String line containing the USD conversion details
     */
    private String printUSD(Currency currency, Double amount) {
        String formattedAmount = String.format("%10.3f", Converter.getInstance().toUSD(currency, amount));
        return (Currency.USD.equals(currency) ? "\n" : " (USD " + formattedAmount + ")\n");
    }

    /**
     * Auxiliary method to print a single payment into a line.
     * 
     * @param currency of the payment
     * @param amount of the payment
     * @return String line containing the payment
     */
    private String printPaymentLine(Currency currency, Double amount) {
        if (amount == 0) {
            return "";
        } else {
            String formattedAmount = String.format("%10.1f", amount);
            return (currency + " " + formattedAmount + printUSD(currency, amount));
        }
    }

    /**
     * Print the payments into console.
     * 
     * @param balance Payments balance
     */
    protected void printPayments(Map<Currency, Double> balance) {
        StringBuilder output = new StringBuilder("\n");
        output.append("Payments Balance **************\n");
        balance.forEach((currency, amount) -> output.append(printPaymentLine(currency, amount)));
        output.append("*******************************\n");
        System.out.println(output);
    }

}
