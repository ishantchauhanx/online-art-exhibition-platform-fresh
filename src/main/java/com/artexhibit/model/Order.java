package com.artexhibit.model;

import java.math.BigDecimal;

public class Order {
    private int orderId;
    private int buyerId;
    private int artworkId;
    private String orderDate;
    private BigDecimal amount;
    private String paymentStatus;

    public Order() {}

    public Order(int orderId, int buyerId, int artworkId, String orderDate, BigDecimal amount, String paymentStatus) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.artworkId = artworkId;
        this.orderDate = orderDate;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }

    public int getArtworkId() { return artworkId; }
    public void setArtworkId(int artworkId) { this.artworkId = artworkId; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
