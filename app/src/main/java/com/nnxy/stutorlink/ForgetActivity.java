package com.nnxy.stutorlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.nnxy.entity.User;
import com.nnxy.okhttp.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetActivity extends AppCompatActivity {
    private OKManager manager;
    private EditText phone,newPwd,rPwd,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        //初始化各组件
        phone = findViewById(R.id.For_PhoneNumber);
        newPwd = findViewById(R.id.For_NewPassword);
        rPwd = findViewById(R.id.For_ConfirmPassword);
        user = findViewById(R.id.For_UserName);
        manager = OKManager.getInstance();
    }

    public void forget_password(View view){
        //获取各输入框的值
        String phoneNum = phone.getText().toString();
        String newPassword = newPwd.getText().toString();
        String rePassword = rPwd.getText().toString();
        String userCode = user.getText().toString();
        if(phoneNum.trim().equals("")||newPassword.trim().equals("")||rePassword.trim().equals("")){
            Toast.makeText(getApplicationContext(),"输入框不能为空！",Toast.LENGTH_LONG).show();
        }else if (!(newPassword.equals(rePassword))){
            Toast.makeText(getApplicationContext(),"两次密码不一致！",Toast.LENGTH_LONG).show();
        }else {
            User user =new User();
            user.setUserCode(userCode);
            user.setUserPhone(phoneNum);
            user.setPassword(newPassword);
            Gson gson =new Gson();
            //将user对象转换为Json字符串
            final String str =gson.toJson(user);
            /**
             * 先检查该用户对应的电话号码是否正确
             */
            manager.sendStringByPostMethod("http://10.0.2.2:8080/stutorlink1/user/checkUserPhone.action", str, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getString("status").equals("success")){
                            /**
                             * 如果电话号码验证成功，就进行修改密码
                             */
                            manager.sendStringByPostMethod("http://10.0.2.2:8080/stutorlink1/user/updatePassword.action",str , new OKManager.Func4() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    if (jsonObject.getString("status").equals("success")){
                                        Intent intent =new Intent(ForgetActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();//服务器响应后应该返回一个json对象数据
                                    }else if (jsonObject.getString("status").equals("error")){
                                        Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

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
}
