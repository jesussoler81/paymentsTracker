package com.bsc.payments.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.bsc.payments.Tracker;

/**
 * Class to convert any currency amount to USD amount. This class works
 * specifically with USD but could be extended to convert to other currencies.
 * It is implemented as Singleton.
 */
public class Converter {

    /** Name of the file containing the rates to be loaded */
    private final static String RATES_FILENAME = "ratesToUSD.properties";

    /** The unique instance of this class */
    private final static Converter INSTANCE = new Converter();

    /** Map containing the rates to USD for each currency */
    private Map<Currency, Double> ratesToUSD = new HashMap<Currency, Double>();

    /**
     * Default Constructor. Loads the rates file.
     */
    private Converter() {
        Properties prop = new Properties();

        try (InputStream input = Tracker.class.getClassLoader().getResourceAsStream(RATES_FILENAME)) {
            if (input == null) {
                System.out.println("Unable to find " + RATES_FILENAME);
                System.exit(0);
            }

            // load a properties file from class path, inside static method
            prop.load(input);
            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                ratesToUSD.put(Currency.valueOf(key), Double.valueOf(value));
            }

        } catch (IOException e) {
            System.out.println("Unable to load " + RATES_FILENAME + ": " + e.getMessage());
            System.exit(0);
        }

        if ((Currency.values().length - 1) != ratesToUSD.size()) {
            System.out.println("Missing exchange rates to USD in file " + RATES_FILENAME);
            System.exit(0);
        }

    }

    /**
     * Get the only instance of this class.
     * 
     * @return Converter object
     */
    public static Converter getInstance() {
        return INSTANCE;
    }

    /**
     * Converts the given amount to USD.
     * 
     * @param currency of the amount to convert
     * @param amount to convert
     * @return amount in USD
     */
    public double toUSD(Currency currency, double amount) {
        if (Currency.USD.equals(currency)) {
            return amount;
        }
        Double rate = ratesToUSD.get(currency);
        return amount * rate;
    }

}
