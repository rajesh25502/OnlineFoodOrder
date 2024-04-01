package com.foodOrder.Service;

import com.foodOrder.Model.Order;
import com.foodOrder.Response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Value("${stripe.api.key}")
    private String stripeSecreteKey;
    @Override
    public PaymentResponse createPaymentLink(Order order) throws Exception {

        Stripe.apiKey=stripeSecreteKey;
        long total=(long)order.getTotalPrice();
        SessionCreateParams params=SessionCreateParams.builder().addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/paymentSuccess"+order.getId())
                .setCancelUrl("http://localhost:8080/paymentFailure")
                .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd").setUnitAmount((Long)total*100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("online-food")
                                        .build()
                                ).build()
                        ).build()
                ).build();

        Session session=Session.create(params);
        PaymentResponse response=new PaymentResponse();
        response.setPaymentUrl(session.getUrl());

        return response;
    }
}
