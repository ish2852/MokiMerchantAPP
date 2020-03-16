package com.example.orderspot_merchant.service;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.orderspot_merchant.Util.HttpRequest;
import com.example.orderspot_merchant.controller.OrderListActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class FirebaseMessaginService extends FirebaseMessagingService {
    public FirebaseMessaginService() {

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        sendRegistrationToServerByToken();
    }

    public String sendRegistrationToServerByToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        try {
            String userId = UserService.userVO.getId();
            new HttpRequest().execute("muser_token_update", userId, token).get();
        } catch (Exception e) {
            Log.e("token Update", e.toString());
        }
        return token;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String data = null;
        if (remoteMessage.getData().size() > 0) {
            data = remoteMessage.getData().get("data");
            sendNotification(data);
        }

        super.onMessageReceived(remoteMessage);
    }

    private void sendNotification(String data) {
        int orderId = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String type = jsonObject.getString("type");
            orderId = jsonObject.getInt("order_ID");
            if (type.equals("user_order")) {
                orderMessageAction(data);
            } else if (type.equals("guser_message_check")) {
                userMessageCheckAction(orderId);
            }
        } catch (Exception e) {
            Log.e("FCM", e.toString());
        }
    }

    public void orderMessageAction(String data) {

        Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
        intent.putExtra("user_order", data);
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP
        );
        getApplicationContext().startActivity(intent);
    }

    public void userMessageCheckAction(int orderId) {
        Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
        intent.putExtra("guser_message_check", orderId);
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP
        );
        getApplicationContext().startActivity(intent);
    }

}
