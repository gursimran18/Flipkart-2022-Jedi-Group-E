package com.flipkart.bean;

public class PaymentBill {
    private String paymentType;

    public PaymentBill(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
