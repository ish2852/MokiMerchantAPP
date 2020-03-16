package com.example.orderspot_merchant.service.Adepter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderspot_merchant.R;
import com.example.orderspot_merchant.Util.Util;
import com.example.orderspot_merchant.controller.OrderActivity;
import com.example.orderspot_merchant.domain.OrderVO;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OrderListRecyclerViewAdepter extends RecyclerView.Adapter<OrderListRecyclerViewAdepter.ViewHolder> {
    List<OrderVO> items;
    Context context;
    OrderVO orderVO;

    public OrderListRecyclerViewAdepter(Context context, List<OrderVO> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public OrderListRecyclerViewAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListRecyclerViewAdepter.ViewHolder viewHolder, final int position) {

        orderVO = items.get(position);

        String orderDate = getOrderDate(orderVO.getOrderApproveTime());
        viewHolder.orderDateTextView.setText(orderDate);
        viewHolder.orderIdTextView.setText(Integer.toString(orderVO.getOrderId()));

        String orderPay = new DecimalFormat("###,###,###").format(orderVO.getOrderPay());
        viewHolder.orderPay.setText(orderPay);

        String orderState = getOrderStateString(orderVO.getOrderState());
        viewHolder.orderStateTextView.setText(orderState);

        String orderContent = getOrderContentByOrderVO(orderVO);
        viewHolder.orderContent.setText(orderContent);

        Picasso.get().load(Resources.getSystem().getString(R.string.s3_bucket_name) + orderVO.getProductList().get(0).getImage()).into(viewHolder.productImage);
    }

    public String getOrderStateString(int state){
        String orderstate = "";
        switch (state) {
            case 0:
                orderstate = "준비 중";
                break;
            case 1:
                orderstate = "제조완료";
                break;
            case 2:
                orderstate = "수령완료";
                break;
            case 3:
                orderstate = "주문취소";
                break;
        }
        return orderstate;
    }

    public String getOrderContentByOrderVO(OrderVO orderVO){
        String orderContentText = "";
        orderContentText += orderVO.getProductList().get(0).getProductName();
        if(orderVO.getProductList().size() > 1){
            orderContentText += " 외 " + (orderVO.getProductList().size() - 1) + "개 품목";
        }
        return orderContentText;
    }

    public String getOrderDate(String date){
        String orderDate = "";
        try {
            Calendar calendar = Util.getCalendarByString(orderVO.getOrderApproveTime());
            orderDate = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        }catch (ParseException e){
            Log.e("orderDate", e.toString());
        }
        return orderDate;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<OrderVO> items) {
        this.items = items;
    }

    public List<OrderVO> getMenulist() {
        return items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderDateTextView, orderIdTextView, orderStateTextView, orderContent, orderPay;
        public LinearLayout orderItem;
        public ImageView productImage;
        public ViewHolder(final View itemView) {
            super(itemView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            orderStateTextView = itemView.findViewById(R.id.orderStateTextView);
            orderContent = itemView.findViewById(R.id.orderContent);
            orderPay = itemView.findViewById(R.id.orderPay);
            productImage = itemView.findViewById(R.id.productImage);
            orderItem = itemView.findViewById(R.id.orderItem);
            orderItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderActivity.class);
                    intent.putExtra("order", items.get(getLayoutPosition()));
                    context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }


    }
}
