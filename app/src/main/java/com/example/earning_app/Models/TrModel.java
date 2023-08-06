package com.example.earning_app.Models;

public class TrModel {

    String coin , status , date , paymentDetails , paymentMethod;

    public TrModel() {
    }

    public TrModel(String coin, String status, String date, String paymentDetails, String paymentMethod) {
        this.coin = coin;
        this.status = status;
        this.date = date;
        this.paymentDetails = paymentDetails;
        this.paymentMethod = paymentMethod;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}


