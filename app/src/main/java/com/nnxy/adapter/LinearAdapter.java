package com.nnxy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nnxy.stutorlink.R;

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder> {
    //设置构造方法，获取Context
    private Context mContext;
    public LinearAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {//注意返回的是LinearAdapter.LinearViewHolder
        //设置每一个item（条目）的样式布局
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearAdapter.LinearViewHolder viewHolder, final int i) {//可以处理点击事件、赋值等等
        //对按钮设置点击事件
        viewHolder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"button1.."+i,Toast.LENGTH_LONG).show();

            }
        });
        viewHolder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"button2.."+i,Toast.LENGTH_LONG).show();

            }
        });
        viewHolder.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"button3.."+i,Toast.LENGTH_LONG).show();

            }
        });
        //对每个item（条目）设置点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"itemView.."+i,Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * 该方法设置的是有多少个可显示的item
     * @return
     */
    @Override
    public int getItemCount() {
        return 30;
    }
    class LinearViewHolder extends RecyclerView.ViewHolder{
        //对item的控件进行声明
        private ImageView headImg,contentImg;
        private TextView titleTv,usernameTv;
        private Button btn1,btn2,btn3;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            //找到控件
            headImg = itemView.findViewById(R.id.head_img);
            contentImg = itemView.findViewById(R.id.content_img);
            titleTv = itemView.findViewById(R.id.title_tv);
            usernameTv = itemView.findViewById(R.id.username_tv);
            btn1 = itemView.findViewById(R.id.button1);
            btn2 = itemView.findViewById(R.id.button2);
            btn3 = itemView.findViewById(R.id.button3);
        }
    }
}
