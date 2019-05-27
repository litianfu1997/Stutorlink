package com.nnxy.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nnxy.adapter.LinearAdapter;
import com.nnxy.stutorlink.LinearRecyclerViewActivity;
import com.nnxy.stutorlink.R;

public class MainViewFragment extends Fragment {
    private RecyclerView mRvmain;//声明RecyclerView

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_view_fragment_layout,container,false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvmain = view.findViewById(R.id.rv_mian);//找到RecycleView布局
        mRvmain.setLayoutManager(new LinearLayoutManager(getActivity()));//设置LayoutManager
        mRvmain.addItemDecoration(new MyDecoration());//设置分割线
        mRvmain.setAdapter(new LinearAdapter(getActivity()));//设置适配器
    }
    /**
     * 绘制下划线
     */
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //设置分割线的具体信息
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }
}
