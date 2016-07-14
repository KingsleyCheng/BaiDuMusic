package com.example.dllo.baidumusic.totalfragment.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.baidumusic.MyApp;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;
import com.example.dllo.baidumusic.main.MainActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by dllo on 16/7/7.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView registerReturn;
    private EditText username, password;
    private Button register;
    private String inputUsername, inputPassword;


    @Override
    public int setLayout() {
        return R.layout.activity_register;

    }

    @Override
    public void activityInitData() {
        registerReturn = (ImageView) findViewById(R.id.register_return);
        register = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        registerReturn.setOnClickListener(this);
        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_return:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.register:
                inputUsername = username.getText().toString();
                inputPassword = password.getText().toString();
                if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(this, "请输入账户名或密码", Toast.LENGTH_SHORT).show();
                } else {
                    BmobUser bmobUser = BmobUser.getCurrentUser(this);
                    if (bmobUser == null) {
                        bmobUser = new BmobUser();
                        bmobUser.setUsername(inputUsername);
                        bmobUser.setPassword(inputPassword);
                        bmobUser.signUp(MyApp.context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent1);
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
        }
    }
}
