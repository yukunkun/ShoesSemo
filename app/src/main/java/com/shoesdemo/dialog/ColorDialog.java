package com.shoesdemo.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.shoesdemo.R;
import com.shoesdemo.activity.AddGoodsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorDialog extends DialogFragment {

    private ListView mListView;
    private List<String> mList=new ArrayList<>();
    public static ColorDialog getInstance(){
        return new ColorDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = inflater.inflate(R.layout.dialog_color, null);
        mListView = inflate.findViewById(R.id.listview);
        TextView tvTitle=inflate.findViewById(R.id.tv_title);
        tvTitle.setText("请选择颜色：");
        mList= Arrays.asList(getResources().getStringArray(R.array.color));
        mListView.setAdapter(new LVAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ((AddGoodsActivity)getActivity()).getColor(mList.get(position));
                dismiss();
            }
        });
        return inflate;
    }

    class LVAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.lv_dialog_item, null);
            TextView textView=inflate.findViewById(R.id.tv_text);
            textView.setText(mList.get(i));
            return inflate;
        }
    }
}
