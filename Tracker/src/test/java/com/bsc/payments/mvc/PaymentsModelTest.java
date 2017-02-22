package com.bsc.payments.mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.bsc.payments.utils.Currency;
import com.bsc.payments.utils.Payment;

public class PaymentsModelTest {

    private Double amount = Double.valueOf(329.21);
    private Double doubleAmount = amount + amount;

    @Test
    public void test1Currency1Amount() {
        PaymentsModel payments = new PaymentsModel();
        payments.add(new Payment(Currency.CZK, amount));

        Map<Currency, Double> balance = payments.getBalance();
        assertEquals(balance.size(), 1);
        for (Entry<Currency, Double> e : balance.entrySet()) {
            assertEquals(e.getKey(), Currency.CZK);
            assertEquals(e.getValue(), amount);
        }
    }

    @Test
    public void test1Currency2Amounts() {
        PaymentsModel payments = new PaymentsModel();
        payments.add(new Payment(Currency.CZK, amount));
        payments.add(new Payment(Currency.CZK, amount));

        Map<Currency, Double> balance = payments.getBalance();
        assertEquals(balance.size(), 1);
        for (Entry<Currency, Double> e : balance.entrySet()) {
            assertEquals(e.getKey(), Currency.CZK);
            assertEquals(e.getValue(), doubleAmount);
        }
    }

    @Test
    public void test2Currencies1Amount() {
        PaymentsModel payments = new PaymentsModel();
        payments.add(new Payment(Currency.EUR, amount));
        payments.add(new Payment(Currency.CZK, doubleAmount));

        Map<Currency, Double> balance = payments.getBalance();
        assertEquals(balance.size(), 2);
        for (Entry<Currency, Double> e : balance.entrySet()) {
            if (Currency.EUR.equals(e.getKey())) {
                assertEquals(e.getValue(), amount);
            } else if (Currency.CZK.equals(e.getKey())) {
                assertEquals(e.getValue(), doubleAmount);
            } else {
                fail("Unexpected currency amount");
            }
        }
    }

    @Test
    public void test2Currencies2Amounts() {
        PaymentsModel payments = new PaymentsModel();
        payments.add(new Payment(Currency.EUR, amount));
        payments.add(new Payment(Currency.EUR, -doubleAmount));
        payments.add(new Payment(Currency.CZK, doubleAmount));
        payments.add(new Payment(Currency.CZK, -amount));

        Map<Currency, Double> balance = payments.getBalance();
        assertEquals(balance.size(), 2);
        for (Entry<Currency, Double> e : balance.entrySet()) {
            if (Currency.EUR.equals(e.getKey())) {
                assertEquals(e.getValue(), Double.valueOf(-amount));
            } else if (Currency.CZK.equals(e.getKey())) {
                assertEquals(e.getValue(), amount);
            } else {
                fail("Unexpected currency amount");
            }
        }
    }
}
