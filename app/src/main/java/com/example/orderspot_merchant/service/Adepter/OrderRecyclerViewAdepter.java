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
import com.example.orderspot_merchant.domain.ProductVO;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OrderRecyclerViewAdepter extends RecyclerView.Adapter<OrderRecyclerViewAdepter.ViewHolder> {
    List<ProductVO> items;

    public OrderRecyclerViewAdepter(List<ProductVO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrderRecyclerViewAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderRecyclerViewAdepter.ViewHolder viewHolder, final int position) {

        ProductVO productVO = items.get(position);
        String productName = productVO.getProductName();
        viewHolder.productNameTextView.setText(productName);
        String AmountAndprice = (new DecimalFormat("###,###,###").format(productVO.getProductPrice())) + "원 X " + productVO.getAmount()  + "개";
        viewHolder.productPriceAndAmountTextView.setText(AmountAndprice);

        Picasso.get().load(Resources.getSystem().getString(R.string.s3_bucket_name) + productVO.getImage()).into(viewHolder.productImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<ProductVO> items) {
        this.items = items;
    }

    public List<ProductVO> getMenulist() {
        return items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView, productPriceAndAmountTextView;
        public ImageView productImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceAndAmountTextView = itemView.findViewById(R.id.productPriceAndAmountTextView);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
