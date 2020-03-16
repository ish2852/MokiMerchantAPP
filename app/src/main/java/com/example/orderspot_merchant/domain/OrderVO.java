package com.example.orderspot_merchant.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderVO implements Serializable {
    private int orderId;
    private String generalId;
    private String merchantId;
    private String orderApproveTime;
    private int orderPay;
    private String requirement;
    private int orderState;
    private  ArrayList<ProductVO> productList;

    public OrderVO(){
        this.productList = new ArrayList<>();
    }

    public void addProductList(ProductVO productVO){
        this.productList.add(productVO);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getGeneralId() {
        return generalId;
    }

    public void setGeneralId(String generalId) {
        this.generalId = generalId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderApproveTime() {
        return orderApproveTime;
    }

    public void setOrderApproveTime(String orderApproveTime) {
        this.orderApproveTime = orderApproveTime;
    }

    public int getOrderPay() {
        return orderPay;
    }

    public void setOrderPay(int orderPay) {
        this.orderPay = orderPay;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public ArrayList<ProductVO> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductVO> productList) {
        this.productList = productList;
    }
}
