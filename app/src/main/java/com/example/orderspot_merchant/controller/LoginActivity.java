package com.example.orderspot_merchant.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderspot_merchant.R;
import com.example.orderspot_merchant.Util.HttpRequest;
import com.example.orderspot_merchant.Util.Util;
import com.example.orderspot_merchant.domain.UserVO;
import com.example.orderspot_merchant.service.UserService;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText idInputText, passwordInputText;
    Button loginButton;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idInputText = findViewById(R.id.idInputText);
        passwordInputText = findViewById(R.id.passwordInputText);
        loginButton = findViewById(R.id.loginButton);

        userService = new UserService(getApplicationContext());
        UserVO userVO = userService.getUser();
        if (userService.isUser()) {
            idInputText.setText(userVO.getId());
            passwordInputText.setText(userVO.getPassword());
            loginRequest();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });
    }

    public void loginRequest() {
        String result = null;
        String id = idInputText.getText().toString();
        String password = passwordInputText.getText().toString();
        try {
            result = new HttpRequest().execute("merchant_login", id, password).get();
            loginResponse(result);
        } catch (Exception e) {
            Log.e("loginActivity", e.toString());
            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginResponse(String result) throws JSONException {
        String success = null;
        JSONObject jsonObject = new JSONObject(result);
        success = jsonObject.getString("success");

        if(Util.isSuccess(success)){
            setAutoLoginUser();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAutoLoginUser(){
        String id = idInputText.getText().toString();
        String password = passwordInputText.getText().toString();
        userService.setUser(id, password);
    }
}
