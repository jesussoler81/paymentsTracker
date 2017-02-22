package com.bsc.payments.utils;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.bsc.payments.utils.Converter;
import com.bsc.payments.utils.Currency;

public class ConverterTest {

    @Test
    public void testGetInstance() {
        Converter converter = Converter.getInstance();
        assertTrue("getInstance returns null", converter != null);
    }

    @Test
    public void testToUSDFromUSD() {
        Double expected = 58.63;
        Double result = Converter.getInstance().toUSD(Currency.USD, 58.63);
        assertTrue("From USD to USD wrong conversion error.", expected.equals(result));
    }

    @Test
    public void testToUSDFromEUR() {
        Converter conv = Converter.getInstance();
        Map<Currency, Double> rates = new HashMap<Currency, Double>();
        // Set specific rate for the test
        rates.put(Currency.EUR, 1.05);
        Whitebox.setInternalState(conv, "ratesToUSD", rates);
        Double expected = 2.10;
        Double result = Converter.getInstance().toUSD(Currency.EUR, 2.0);
        assertTrue("From nonUSD to USD conversion error.", expected.equals(result));
    }

}
