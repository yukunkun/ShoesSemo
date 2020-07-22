package com.shoesdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoesdemo.BaseActivity;
import com.shoesdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ShoesDetailActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<String> mList=new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private int mCurrentPosition = 0;
    private int mSuspensionHeight=0;
    private RVAdapter mRvAdapter;

    public static void start(Context context){
        context.startActivity(new Intent(context,ShoesDetailActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_shoes_detail;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 20; i++) {
            mList.add("number:"+i);
        }
        mRecyclerView = findViewById(R.id.recycler);
        mSuspensionBar=findViewById(R.id.ll);
        mSuspensionTv=findViewById(R.id.tv_head);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRvAdapter = new RVAdapter();
        mRecyclerView.setAdapter(mRvAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mLinearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                mSuspensionHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                long itemId = mRvAdapter.getItemId(mCurrentPosition + 1);
                if(itemId %2==0){
                    View view = mLinearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                    //下一个item距离top位置小于header
                    if (view != null) {
                        if (view.getTop() <= mSuspensionHeight) {
                            mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                        } else {
                            mSuspensionBar.setY(0);
                        }
                    }
                }
                if (mCurrentPosition != mLinearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                    updateSuspensionBar();
                    mSuspensionBar.setY(0);
                }
            }
        });
        updateSuspensionBar();
    }

    private void updateSuspensionBar() {
        mSuspensionTv.setText("header:" + mCurrentPosition);
    }

    class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(mContext).inflate(R.layout.rv_item_shoes,null);
            return new ShoesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ShoesViewHolder){
                ((ShoesViewHolder) holder).mTvGoods.setText("货号："+mList.get(position));
                ((ShoesViewHolder) holder).mTvColor.setText("颜色："+mList.get(position));
                ((ShoesViewHolder) holder).mTvSize.setText(mList.get(position)+"码");
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mList.size();
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
}
