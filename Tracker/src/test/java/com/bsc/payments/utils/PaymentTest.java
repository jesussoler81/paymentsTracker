package com.bsc.payments.utils;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PaymentTest {

    private Double amount = Double.valueOf(100.45);

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setErr(null);
    }

    @Test
    public void testPaymentConstructor() {
        Payment payment = new Payment(Currency.CZK, amount);
        assertEquals(payment.getCurrency(), Currency.CZK);
        assertEquals(payment.getAmount(), amount);
    }

    @Test
    public void testCreatePayment() {
        Payment payment = Payment.createPayment("CZK", amount.toString());
        assertEquals(payment.getCurrency(), Currency.CZK);
        assertEquals(payment.getAmount(), amount);
    }

    @Test
    public void testCreatePaymentCurrencyError() {
        Payment payment = Payment.createPayment("CZZ", amount.toString());
        assertEquals(payment, null);
        String expected = "Couldn't convert 'CZZ' to a currency and '100.45' to an amount.\r\n";
        assertEquals(expected, errContent.toString());
    }

    @Test
    public void testCreatePaymentAmountError() {
        Payment payment = Payment.createPayment("CZK", "P12E5");
        assertEquals(payment, null);
        String expected = "Couldn't convert 'CZK' to a currency and 'P12E5' to an amount.\r\n";
        assertEquals(expected, errContent.toString());
    }
}