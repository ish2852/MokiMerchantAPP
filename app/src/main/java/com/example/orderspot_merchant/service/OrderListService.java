package com.example.orderspot_merchant.service;

import android.util.Log;

import com.example.orderspot_merchant.domain.OrderVO;
import com.example.orderspot_merchant.domain.ProductVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderListService {
    ArrayList<OrderVO> orderList;
    OrderVO orderVO;

    public void setOrderList(String jsonString) {
        orderList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                setOrder(jsonArray.get(i).toString());
                orderList.add(orderVO);
            }
        } catch (Exception e) {
            Log.e("OrderListService", e.toString());
        }
    }

    public void setOrder(String jsonString) throws JSONException {
        JSONObject order = new JSONObject(jsonString);
        orderVO = new OrderVO();

        orderVO.setOrderId(order.getInt("order_ID"));
        orderVO.setGeneralId(order.getString("GeneralUser_guser_ID"));
        orderVO.setMerchantId(order.getString("MerchantUser_muser_ID"));
        orderVO.setOrderApproveTime(order.getString("orderApproveTime"));
        orderVO.setOrderPay(order.getInt("orderPay"));
        orderVO.setRequirement(order.getString("orderRequirement"));
        orderVO.setOrderState(order.getInt("orderState"));

        JSONArray productList = new JSONArray(order.getString("product"));
        for (int i = 0; i < productList.length(); i++) {
            setProudct(productList.get(i).toString());
        }

    }

    public void setProudct(String jsonString) throws JSONException {
        JSONObject product = new JSONObject(jsonString);
        ProductVO productVO = new ProductVO();
        productVO.setAmount(product.getInt("Amount"));
        productVO.setImage(product.getString("productImage"));
        productVO.setProductId(product.getString("product_ID"));
        productVO.setProductName(product.getString("productName"));
        productVO.setProductPrice(product.getInt("productPrice"));
        orderVO.addProductList(productVO);
    }

    public ArrayList<OrderVO> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderVO> orderList) {
        this.orderList = orderList;
    }

    public OrderVO getOrderVO() {
        return orderVO;
    }

    public void setOrderVO(OrderVO orderVO) {
        this.orderVO = orderVO;
    }

}
