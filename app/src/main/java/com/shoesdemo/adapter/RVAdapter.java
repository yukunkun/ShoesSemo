package com.shoesdemo.adapter;

import android.content.Context;
import android.util.Log;
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

            int section = getSectionForPosition(position);
            if(position == getPositionForSection(section)){
                ((ShoesViewHolder) holder).mTvHeader.setVisibility(View.VISIBLE);
                ((ShoesViewHolder) holder).mTvHeader.setText(mShoesModules.get(position).getLetter());
            }else{
                ((ShoesViewHolder) holder).mTvHeader.setVisibility(View.GONE);
            }

            ((ShoesViewHolder) holder).mTvGoods.setText("货号："+mShoesModules.get(position).getShoes().getGoodsNo());
            ((ShoesViewHolder) holder).mTvColor.setText("颜色："+mShoesModules.get(position).getShoes().getColor());
            ((ShoesViewHolder) holder).mTvSize.setText(mShoesModules.get(position).getShoes().getSize()+"码");
        }
    }

    @Override
    public int getItemCount() {
        return mShoesModules.size();
    }

    /**
     * 选中的位置
     */
    public int getSectionForPosition(int position) {
        return mShoesModules.get(position).getLetter().charAt(0);
    }

    /**
     * 位置是否有,基本就能实现了，
     */
    public int getPositionForSection( int section) {
        for (int i = 0; i < mShoesModules.size(); i++) {
            String sortStr = mShoesModules.get(i).getLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    class ShoesViewHolder extends RecyclerView.ViewHolder{
        TextView mTvGoods,mTvColor,mTvSize;
        TextView mTvHeader;
        public ShoesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvGoods=itemView.findViewById(R.id.tv_good);
            mTvColor=itemView.findViewById(R.id.tv_color);
            mTvSize=itemView.findViewById(R.id.tv_size);
            mTvHeader=itemView.findViewById(R.id.tv_header);
        }
    }

}
