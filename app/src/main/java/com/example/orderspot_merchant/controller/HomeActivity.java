package com.example.orderspot_merchant.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.orderspot_merchant.R;
import com.example.orderspot_merchant.Util.HttpRequest;
import com.example.orderspot_merchant.service.UserService;

public class HomeActivity extends AppCompatActivity {
    Button orderListButton, salesSearchButton, searchingReviewButton, logoutButton;
    TextView userIdTextView;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userService = new UserService(getApplicationContext());
        userIdTextView = findViewById(R.id.userIdTextView);
        logoutButton = findViewById(R.id.logoutButton);
        orderListButton = findViewById(R.id.orderListButton);
        salesSearchButton = findViewById(R.id.salesSearchButton);
        searchingReviewButton = findViewById(R.id.searchingReviewButton);

        userIdTextView.setText(userService.getUserId());

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpRequest().execute("muser_token_update", userService.getUserId(), null);
                userService.userLogout();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        salesSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                startActivity(intent);
            }
        });

        orderListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
                startActivity(intent);
            }
        });
    }
}
