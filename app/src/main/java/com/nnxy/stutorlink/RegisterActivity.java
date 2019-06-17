package com.nnxy.stutorlink;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.nnxy.gjp.entity.User;
//import com.nnxy.gjp.okhttp.OKManager;
import com.google.gson.Gson;
import com.nnxy.entity.User;
import com.nnxy.okhttp.OKManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private EditText ed_username,ed_password,ed_repassword,ed_phoneNum,ed_nicheng,ed_birthday;
    private RadioGroup rg_sex,rg_professional;
    private RadioButton rb_sex,rb_professional;
    private Calendar calendar;
    private int mYear,mMonth,mDay;
    private OKManager manager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        manager = OKManager.getInstance();//获取OKmanager对象
        ed_username = findViewById(R.id.Reg_username);
        ed_password = findViewById(R.id.Reg_password);
        ed_repassword = findViewById(R.id.Reg_repassword);
        ed_phoneNum = findViewById(R.id.Reg_phoneNum);
        ed_nicheng = findViewById(R.id.Reg_name);
        ed_birthday = findViewById(R.id.Reg_birthday);
        rg_sex = findViewById(R.id.Reg_sex);
        rg_professional = findViewById(R.id.Reg_professional);
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 rb_sex = group.findViewById(checkedId);
            }
        });
        rg_professional.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb_professional = group.findViewById(checkedId);
            }
        });
        //设置日期选择器
        calendar=Calendar.getInstance();
        ed_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //日历控件，注意在activity里使用类名.this来代替context，在Fragment中使用getActivity()
                new DatePickerDialog(RegisterActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        mYear = year;
                        mMonth = month;
                        mDay = day;
                        ed_birthday.setText(new StringBuilder()
                                .append(mYear).append("-").append((mMonth+1) < 10 ? "0"+(mMonth+1):(mMonth+1)).append("-").append((mDay<10)?"0"+mDay:mDay));
                    }
                },calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




    }
    /**
     * 用户注册功能
     * @param view
     */
    public void Register_NewUser(View view){
        String userCode = null,password = null,repassword = null,userPhone = null,userName = null,birthday = null,sex = null,professional = null;
        if (ed_username.getText() != null & ed_password.getText() != null & ed_repassword != null
            & ed_birthday.getText() != null & rb_sex.getText() != null && rb_professional.getText() != null
            &ed_phoneNum.getText()!=null){
            //获取输入框的值
            userCode = ed_username.getText().toString();
            password = ed_password.getText().toString();
            repassword = ed_repassword.getText().toString();
            userPhone = ed_phoneNum.getText().toString();
            userName = ed_nicheng.getText().toString();
            birthday = ed_birthday.getText().toString();
            sex = rb_sex.getText().toString();
            professional = rb_professional.getText().toString();

        }else {
            Toast.makeText(getApplicationContext(),"输入框不要留空",Toast.LENGTH_LONG).show();

        }
        //进行条件判断
        if(userCode.trim().equals("")||password.trim().equals("")||repassword.trim().equals("")||userPhone.trim().equals("")||userName.trim().equals("")
            ||birthday.trim().equals("")||sex.equals("")||professional.equals("")){
            Toast.makeText(getApplicationContext(),"输入框不要留空",Toast.LENGTH_LONG).show();
        }else if(!(password.equals(repassword))){
            Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_LONG).show();
        }else {
            //将获取到的数据填入user对象中
            User user = new User();
            user.setUserCode(userCode);
            user.setPassword(password);
            user.setUserPhone(userPhone);
            user.setUserName(userName);
            user.setBirthday(birthday);
            user.setProfessional(professional);
            user.setSex(sex);
            Gson gson =new Gson();//实例化Gson对象
            String userJson = gson.toJson(user);//将user对象转换为Json字符串
            //将Json字符串发给服务器
            //http://www.tech4flag.com  http://10.0.2.2:8080
            manager.sendStringByPostMethod("http://10.0.2.2:8080/stutorlink1/user/register.action", userJson, new OKManager.Func4() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        //对服务器返回的Json对象进行解析
                        if (jsonObject.getString("status").equals("success")){
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();//服务器响应后应该返回一个json对象数据
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
