package com.example.orderspot_merchant.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.orderspot_merchant.R;
import com.example.orderspot_merchant.Util.HttpRequest;
import com.example.orderspot_merchant.Util.Util;
import com.example.orderspot_merchant.domain.OrderVO;
import com.example.orderspot_merchant.service.Adepter.OrderListRecyclerViewAdepter;
import com.example.orderspot_merchant.service.OrderListService;
import com.example.orderspot_merchant.service.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderListActivity extends AppCompatActivity {
    UserService userService;
    RecyclerView.Adapter adapter;
    RecyclerView orderListRecyclerView;
    OrderListService orderListService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        orderListRecyclerView = findViewById(R.id.orderListRecyclerView);
        orderListService = new OrderListService();
        userService = new UserService(getApplicationContext());

        onResume();
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String userOrder = getIntent().getStringExtra("user_order");
        int orderId = getIntent().getIntExtra("guser_message_check", 0);
        if(userOrder != null) {
            Toast.makeText(getApplicationContext(), "주문이 접수 되었습니다.", Toast.LENGTH_SHORT).show();
            if(orderListService.getOrderList() != null){
                addOrderList(userOrder);
            }
        }else if(orderId != 0){
            Toast.makeText(getApplicationContext(), orderId + " : 제품을 수령할 예정입니다.", Toast.LENGTH_SHORT).show();
            if(orderListService.getOrderList() != null){
                updateOrderState(orderId);
            }
        }

        connectRecyclerView();
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String orderList = orderListRequest();
        orderListService.setOrderList(orderList);
        connectRecyclerView();
    }
    public void addOrderList(String userOrder){
        try {
            orderListService.setOrder(userOrder);
        }catch (JSONException e){
            Log.e("OrderActivity", e.toString());
        }
    }

    public void updateOrderState(int orderId){
        ArrayList<OrderVO> orderList = orderListService.getOrderList();
        for(int i=0; i < orderList.size(); i++){
            OrderVO orderVO = orderList.get(i);
            if(orderVO.getOrderId() == orderId){
                orderVO.setOrderState(2);
                break;
            }
        }
    }

    public String orderListRequest(){
        String result = null;
        try {
            result = new HttpRequest().execute("merchant_order_list", userService.getUserId(), Util.getToDayString()).get();
        } catch (Exception e) {
            Log.e("MenuActivity", e.toString());
        }
        return result;
    }

    public void connectRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        orderListRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OrderListRecyclerViewAdepter(getApplicationContext(), orderListService.getOrderList());
        orderListRecyclerView.setAdapter(adapter);
    }

}
