package com.flipkart.bean;

public class UPIPayment {
    private String upiId;
    private int amount;

    public UPIPayment(String upiId, int amount) {
        this.upiId = upiId;
        this.amount = amount;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
