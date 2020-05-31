package com.flag.myapplication.zuoye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    public static Activity context;
    private List<String> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruitName;
        View view1;

        public ViewHolder(View view) {
            super(view);
            fruitName = (TextView) view.findViewById(R.id.fruitname);
            view1 =view;


        }

    }

    public FruitAdapter(List<String> fruitList,Activity context) {
        mFruitList = fruitList;
        this.context=context;


    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String fruit = mFruitList.get(position);
        holder.fruitName.setText("我是第"+fruit);

        holder.view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FruitAdapter.context,Main2Activity.class);
                intent.putExtra("index",position);
                FruitAdapter.context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}