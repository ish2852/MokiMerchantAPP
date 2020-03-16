package com.example.orderspot_merchant.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderspot_merchant.R;
import com.example.orderspot_merchant.Util.HttpRequest;
import com.example.orderspot_merchant.Util.Util;
import com.example.orderspot_merchant.domain.OrderVO;
import com.example.orderspot_merchant.service.Adepter.OrderRecyclerViewAdepter;

import org.json.JSONObject;

public class OrderActivity extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    RecyclerView productListRecyclerView;
    Button orderCompletedButton, orderCancelButton;
    TextView orderRequirementTextView;

    OrderVO orderVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        productListRecyclerView = findViewById(R.id.productListRecyclerView);
        orderCompletedButton = findViewById(R.id.orderCompletedButton);
        orderCancelButton = findViewById(R.id.orderCancelButton);
        orderRequirementTextView = findViewById(R.id.orderRequirementTextView);
    }

    @Override
    protected void onResume() {
        Intent intent = getIntent();
        orderVO = (OrderVO) intent.getSerializableExtra("order");
        orderRequirementTextView.setText(orderVO.getRequirement());

        setOrderCompletedButtonByState(orderVO.getOrderState());
        setOrderCancelButtonByState(orderVO.getOrderState());
        connectRecyclerView();
        super.onResume();
    }

    public void setOrderCompletedButtonByState(int state){
        if(state == 0){
            orderCompletedButton.setVisibility(View.VISIBLE);
            orderCompletedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderStateRequest("merchant_completed");
                }
            });
        }else{
            orderCompletedButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setOrderCancelButtonByState(int state){
        if(state != 3) {
            orderCancelButton.setVisibility(View.VISIBLE);
            orderCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderStateRequest("merchant_order_cancel");
                }
            });
        }else{
            orderCancelButton.setVisibility(View.INVISIBLE);
        }
    }

    public void orderStateRequest(String type){
        try{
            String result = new HttpRequest().execute(type, orderVO.getMerchantId(), Integer.toString(orderVO.getOrderId())).get();
            String success = new JSONObject(result).getString("success");
            makeToast(success);
        }catch (Exception e){
            Log.e("orderActivity", e.toString());
        }
    }

    public void makeToast(String success){
        String toastString;
        if(Util.isSuccess(success)){
            toastString = "요청이 승인되었습니다.";
        }else{
            toastString = "요청에 실패했습니다.";
        }
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void connectRecyclerView() {
        productListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderRecyclerViewAdepter(orderVO.getProductList());
        productListRecyclerView.setAdapter(adapter);
    }
}
