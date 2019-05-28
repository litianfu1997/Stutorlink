package com.nnxy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nnxy.stutorlink.R;

public class UserViewFragment extends Fragment {
    private LinearLayout userDetailsLayout,IDSafeLayout,userProtocolLayout,privacyProtocolLayout,communityProtocolLayout;//声明控件
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建个人中心样式布局
        View view = inflater.inflate(R.layout.user_view_fragment_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //用户信息以及用户信息编辑
        userDetailsLayout = view.findViewById(R.id.user_detail_linearlayout);//找到控件
        //对控件添加一个点击事件
        userDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到用户编辑界面
                getFragmentManager().beginTransaction().replace(R.id.frame_container,new UserDetailsViewFragment())
                        .addToBackStack(null).commitAllowingStateLoss();
            }
        });
        //跳转到账号安全界面
        IDSafeLayout = view.findViewById(R.id.IDSafe_layout);
        IDSafeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container,new IDSafeViewFragment())
                        .addToBackStack(null).commitAllowingStateLoss();
            }
        });
        //跳转到用户协议界面
        userProtocolLayout = view.findViewById(R.id.userProtocol_layout);
        userProtocolLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container,new UserProtocolViewFragment())
                        .addToBackStack(null).commitAllowingStateLoss();
            }
        });
        //跳转到隐私协议界面
        privacyProtocolLayout = view.findViewById(R.id.privacyProtocol_layout);
        privacyProtocolLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container,new PrivacyProtocolViewFragment())
                        .addToBackStack(null).commitAllowingStateLoss();
            }
        });
        //跳转到社区协议界面
        communityProtocolLayout = view.findViewById(R.id.communityProtocol_layout);
        communityProtocolLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container,new CommunityProtocolViewFragment())
                        .addToBackStack(null).commitAllowingStateLoss();
            }
        });
    }
}
