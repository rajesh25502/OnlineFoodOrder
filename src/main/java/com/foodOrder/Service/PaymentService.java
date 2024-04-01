package com.foodOrder.Service;


import com.foodOrder.Model.Order;
import com.foodOrder.Response.PaymentResponse;

public interface PaymentService {

    public PaymentResponse createPaymentLink(Order order)throws Exception;
}
