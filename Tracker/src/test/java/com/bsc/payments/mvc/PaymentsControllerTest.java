package com.bsc.payments.mvc;

import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import com.bsc.payments.utils.Currency;
import com.bsc.payments.utils.Payment;

public class PaymentsControllerTest {

    @Test
    public void testPaymentsController() {
        PaymentsModel model = Mockito.mock(PaymentsModel.class);
        Map<Currency, Double> balance = null;
        Mockito.when(model.getBalance()).thenReturn(balance);
        PaymentsView view = Mockito.mock(PaymentsView.class);
        PaymentsController controller = new PaymentsController();
        Whitebox.setInternalState(controller, "model", model);
        Whitebox.setInternalState(controller, "view", view);
        Payment payment = Mockito.mock(Payment.class);
        // Model invocation
        controller.updateModel(payment);
        Mockito.verify(model).add(payment);
        // View invocation
        controller.updateView();
        Mockito.verify(view).printPayments(balance);
    }

}
