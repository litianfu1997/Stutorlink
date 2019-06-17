package com.nnxy.stutorlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.dbmanager.CommomUtils;
//import com.google.gson.Gson;
//import com.nnxy.gjp.application.MyApplication;
//import com.nnxy.gjp.entity.Account;
//import com.nnxy.gjp.entity.User;
//import com.nnxy.gjp.okhttp.OKManager;
import com.google.gson.Gson;
import com.nnxy.application.App;
import com.nnxy.entity.User;
import com.nnxy.okhttp.OKManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TGA = MainActivity.class.getSimpleName();

    private OKManager manager ;

    private EditText userCode,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到组件
        userCode = findViewById(R.id.ed_username);
        password = findViewById(R.id.ed_password);
        //获取网络框架manager
        manager = OKManager.getInstance();
    }

    /**
     * 退出
     * @param view
     */
//    public void Exit_fun(View view){
//        CommomUtils commomUtils = new CommomUtils(getApplicationContext());
//        commomUtils.deleteTable();
//        finish();
//    }

    /**
     * 忘记密码
     * @param view
     */
    public void Forget_Fun(View view){
        startActivity(new Intent(MainActivity.this,ForgetActivity.class));

    }


    /**
     * 该按钮实现登录功能，并且跳转到菜单页面
     * @param view
     */
    public void Login_Fun(View view){
        final String userCodeStr =  userCode.getText().toString();
        String pwd = password.getText().toString();
        if(userCodeStr.trim().equals("")||pwd.trim().equals("")){
            Toast.makeText(getApplicationContext(),"用户名和密码不能为空",Toast.LENGTH_LONG).show();
        }else{
            User user =new User();
            user.setUserCode(userCodeStr);
            user.setPassword(pwd);
            final Gson gson =new Gson();
            String str =gson.toJson(user);
            manager.sendStringByPostMethod("http://10.0.2.2:8080/stutorlink1/user/login.action", str, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) { //将服务器表单提交到
                    try {
                        if(jsonObject.getString("status").equals("success")){
                            //将user放入MyApplication
                            App.setUser(jsonObject.getJSONObject("obj"));
                            final Gson gson = new Gson();
                            //通过服务器传过来的json字符串获取user对象
                            User user =gson.fromJson(App.getUser().toString(), User.class);
                            String userJsonStr = gson.toJson(user);
                            //跳转到登录界面
                            Intent intent = new Intent(MainActivity.this,DockMenuActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                        }else if (jsonObject.getString("status").equals("error")){
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }


    /**
     * 该按钮实现的功能是跳转到注册页面
     * @param view
     */
    public void Intent_reg(View view){
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
    }
}
