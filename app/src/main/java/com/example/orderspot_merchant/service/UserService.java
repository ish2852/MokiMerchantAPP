package com.example.orderspot_merchant.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.orderspot_merchant.domain.UserVO;

public class UserService {
    SharedPreferences sharedPreference;
    static UserVO userVO = new UserVO();

    public UserService(Context context){
        sharedPreference  = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        userVO.setId(getAutoLoginUserId());
        userVO.setPassword(getAutoLoginUserPassword());
    }

    public void setUser(String id, String password){
        setUserVO(id, password);

        SharedPreferences.Editor autoLogin = sharedPreference.edit();
        autoLogin.putString("id", id);
        autoLogin.putString("password", password);

        if(firebaseTokenCheck() == null){
            String token = new FirebaseMessaginService().sendRegistrationToServerByToken();
            autoLogin.putString("token", token);
        }

        autoLogin.commit();
    }

    private String firebaseTokenCheck(){
        return sharedPreference.getString("token", null);
    }

    public void setUserVO(String id, String password) {
        userVO.setId(id);
        userVO.setPassword(password);
    }

    public UserVO getUser(){
        return userVO;
    }

    public String getAutoLoginUserId(){
        return sharedPreference.getString("id", null);
    }

    public String getAutoLoginUserPassword(){
        return sharedPreference.getString("password", null);
    }

    public String getUserId(){
        return userVO.getId();
    }

    public boolean isUser(){
        boolean userCheck = true;

        if(userVO.getId() == null)
            userCheck = false;
        else if(userVO.getPassword() == null)
            userCheck = false;

        return userCheck;
    }

    public void userLogout(){
        setUserVO(null, null);
        SharedPreferences.Editor autoLogin = sharedPreference.edit();
        autoLogin.putString("id", null);
        autoLogin.putString("password", null);
        autoLogin.putString("token", null);
        autoLogin.commit();
    }
}
