package com.bsc.payments.mvc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.bsc.payments.utils.Payment;

/**
 * Class in charge of connecting the model and the view
 */
public class PaymentsController {

    /** The model */
    private PaymentsModel model;
    /** The view */
    private PaymentsView view;

    /** Lock to control readers and writers */
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    /** Readers lock */
    private final Lock r = rwl.readLock();
    /** Writers lock */
    private final Lock w = rwl.writeLock();

    /**
     * Default Constructor. Creates a model and view objects.
     */
    public PaymentsController() {
        this.model = new PaymentsModel();
        this.view = new PaymentsView();
    }

    /**
     * Updates the view
     */
    public void updateView() {
        r.lock();
        try {
            view.printPayments(model.getBalance());
        } finally {
            r.unlock();
        }
    }

    /**
     * Updates the model with the specific payment
     * 
     * @param payment to be added
     */
    public void updateModel(Payment payment) {
        w.lock();
        try {
            model.add(payment);
        } finally {
            w.unlock();
        }
    }

}
