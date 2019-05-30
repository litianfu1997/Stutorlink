package com.nnxy.stutorlink;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.nnxy.fragment.MainViewFragment;
import com.nnxy.fragment.PutOutViewFragment;
import com.nnxy.fragment.UserViewFragment;
import com.nnxy.putout.MainFragment;

public class DockMenuActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //点击第一个按钮将所有动态显示
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new MainViewFragment()).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, MainFragment.newInstance())
                            .commitAllowingStateLoss();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new UserViewFragment()).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock_menu);
        //获取底部dock栏
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //点击登陆后，马上显示动态
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,new MainViewFragment()).commitAllowingStateLoss();

    }

}
