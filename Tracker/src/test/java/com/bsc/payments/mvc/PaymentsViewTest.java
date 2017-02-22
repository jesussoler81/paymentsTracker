package com.bsc.payments.mvc;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bsc.payments.utils.Converter;
import com.bsc.payments.utils.Currency;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Converter.class)
public class PaymentsViewTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    @Ignore
    public void test() {
        Converter convMock = Mockito.mock(Converter.class);
        Mockito.when(convMock.toUSD(Currency.EUR, 100)).thenReturn(106.150);
        PowerMockito.mockStatic(Converter.class);
        PowerMockito.when(Converter.getInstance()).thenReturn(convMock);

        PaymentsView view = new PaymentsView();
        Map<Currency, Double> log = new HashMap<Currency, Double>();
        log.put(Currency.USD, 50.0); // No conversion case
        log.put(Currency.EUR, 100.0);// Conversion case
        log.put(Currency.CZK, 0.0); // No display case
        view.printPayments(log);

        StringBuilder output = new StringBuilder("\n");
        output.append("Payments Balance **************\n");
        output.append("USD       50.0\n");
        output.append("EUR      100.0 (USD    106.150)\n");
        output.append("*******************************\n\n");

        assertTrue(output.toString().equals(outContent.toString()));
    }

}
