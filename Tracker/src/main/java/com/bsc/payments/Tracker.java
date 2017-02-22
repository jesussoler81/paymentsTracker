package com.bsc.payments;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.bsc.payments.mvc.PaymentsController;
import com.bsc.payments.utils.Payment;

/**
 * Main class implement the Payments tracker. It uses MVC design pattern.
 */
public class Tracker {

    /** Word that makes the application to finish */
    public final static String EXIT_KEYWORD = "quit";
    /** String used to separate currency from amount in the input */
    public final static String INPUT_SEPARATOR = " ";

    public static void main(String[] args) {

        PaymentsController controller = new PaymentsController();

        // Load parameter file if requested
        if (args.length == 1) {
            Tracker.loadFile(args[0], controller);
        } else if (args.length > 1) {
            System.out.println("Wrong input parameters. Usage: Tracker filename");
            return;
        }

        // Launch thread to print the balance every minute
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                controller.updateView();
            }
        }, 0, 1, TimeUnit.MINUTES);

        // Loop on console input
        readConsolePayments(controller);

        // Shutdown
        exec.shutdown();
        try {
            exec.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Interruption while waiting for updateView thread to finish.");
        }

    }

    /**
     * Loads the payments contained in the file in the PaymentsController
     * 
     * @param fileName Full path to the file to be loaded
     * @param controller PaymentsController
     */
    private static void loadFile(String fileName, PaymentsController controller) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            int lineCounter = 0;
            while ((line = br.readLine()) != null) {
                lineCounter++;
                Payment payment = null;
                String[] tokens = line.split(INPUT_SEPARATOR);
                if (tokens.length != 2) {
                    System.err.println("Error while parsing line " + lineCounter + ": " + line);
                } else {
                    if ((payment = Payment.createPayment(tokens[0], tokens[1])) == null) {
                        System.err.println("Error while parsing line " + lineCounter + ": " + line);
                    } else {
                        controller.updateModel(payment);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("WARNING!!! Couldn't load the entire file the following error occurred: " + e.getMessage());
        }
    }

    /**
     * Loads payments inputed in console into the controller. It finishes once
     * the EXIT_KEYWORD is inputed in console.
     * 
     * @param controller Payments controller
     */
    private static void readConsolePayments(PaymentsController controller) {
        Console console = System.console();
        if (console != null) {
            while (true) {
                String inputString = console.readLine("> ");
                String[] tokens = inputString.split(INPUT_SEPARATOR);
                if (tokens.length == 1 && EXIT_KEYWORD.equals(tokens[0])) {
                    System.out.println("See you!!!");
                    return;
                } else if (tokens.length == 2) {
                    Payment payment = Payment.createPayment(tokens[0], tokens[1]);
                    if (payment != null) {
                        controller.updateModel(payment);
                    }
                } else {
                    System.out.println("Wrong input format:");
                    System.out.println("  It must be:'currency" + INPUT_SEPARATOR + "amount'. E.g:> USD" + INPUT_SEPARATOR + "13.25");
                    System.out.println("  Or type 'quit' to finish");
                }
            }
        }
    }

}
