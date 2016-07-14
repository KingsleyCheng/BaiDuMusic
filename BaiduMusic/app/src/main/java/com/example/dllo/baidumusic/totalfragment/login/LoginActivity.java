package com.example.dllo.baidumusic.totalfragment.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;
import com.example.dllo.baidumusic.main.MainActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by dllo on 16/7/7.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button login;
    private TextView immediatelyRegister;
    private ImageView loginReturn;
    private EditText username, password;
    private String inputUsername, inputPassword;



    @Override
    public int setLayout() {
        return R.layout.activity_login;

    }

    @Override
    public void activityInitData() {
        login = (Button) findViewById(R.id.login);
        immediatelyRegister = (TextView) findViewById(R.id.immediately_register);
        loginReturn = (ImageView) findViewById(R.id.login_return);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        login.setOnClickListener(this);
        immediatelyRegister.setOnClickListener(this);
        loginReturn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_return://退出
                Intent intent = new Intent(this, LoginImmediatelyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.immediately_register://快速注册
                Intent intent1 = new Intent(this, RegisterActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.login://登录
                inputUsername = username.getText().toString();
                inputPassword = password.getText().toString();
                if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                } else {
                    BmobUser bmobUser = BmobUser.getCurrentUser(this);
                    if (bmobUser == null) {
                        bmobUser = new BmobUser();
                        bmobUser.setUsername(inputUsername);
                        bmobUser.setPassword(inputPassword);
                        bmobUser.login(this, new SaveListener() {
                            @Override
                            public void onSuccess() {
//                            SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
//                            SharedPreferences.Editor editor=sp.edit();
//                            editor.putInt("1",1);
//                            editor.commit();


                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                //发送广播刷新数据
                                Intent intent = new Intent(getPackageName() + ".likeSongAdd");
                                sendBroadcast(intent);
                                //发送广播更换图标
                                Intent intent2 = new Intent(getPackageName()+".changeLogin");
                                sendBroadcast(intent2);
                                finish();

                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(this, "已经登录", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

    }
}
