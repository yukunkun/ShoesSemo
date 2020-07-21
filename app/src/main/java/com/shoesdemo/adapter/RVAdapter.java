package com.shoesdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoesdemo.R;
import com.shoesdemo.data.ShoesModule;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ShoesModule> mShoesModules;
    Context mContext;

    public RVAdapter(List<ShoesModule> shoesModules, Context context) {
        mShoesModules = shoesModules;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.rv_item_shoes,null);
        return new ShoesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ShoesViewHolder){
            ((ShoesViewHolder) holder).mTvGoods.setText("货号："+mShoesModules.get(position).getShoes().getGoodsNo());
            ((ShoesViewHolder) holder).mTvColor.setText("颜色："+mShoesModules.get(position).getShoes().getColor());
            ((ShoesViewHolder) holder).mTvSize.setText(mShoesModules.get(position).getShoes().getSize()+"码");
        }
    }

    @Override
    public int getItemCount() {
        return mShoesModules.size();
    }

    class ShoesViewHolder extends RecyclerView.ViewHolder{
        TextView mTvGoods,mTvColor,mTvSize;

        public ShoesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvGoods=itemView.findViewById(R.id.tv_good);
            mTvColor=itemView.findViewById(R.id.tv_color);
            mTvSize=itemView.findViewById(R.id.tv_size);
        }
    }
}
