package com.flipkart.bean;

public class CardPayment {
    private String cardNumber;
    private String expirationDate;
    private String name;
    private int amount;

    /**
     *
     * @param cardNumber
     * @param expirationDate
     * @param name
     * @param amount
     */
    public CardPayment(String cardNumber, String expirationDate, String name, int amount) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.name = name;
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    /**
     *
     * @param cardNumber
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     *
     * @return
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     *
     * @param expirationDate
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
