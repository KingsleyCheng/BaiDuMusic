package com.example.dllo.baidumusic.totalfragment.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.baidumusic.MyApp;
import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;
import com.example.dllo.baidumusic.eventbean.EventTimingClosure;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.BmobUser;

/**
 * Created by dllo on 16/7/7.
 */
public class LoginImmediatelyActivity extends BaseActivity implements View.OnClickListener {
    private TextView loginImmediately, loginName, exitLogin;
    private ImageView wifi, alarmClock;
    private ImageView loginImmediatelyReturn;
    private LinearLayout linearLayoutLogin;
    private LinearLayout linearLayoutExitLogin;


    @Override
    public int setLayout() {
        return R.layout.activity_login_immediately;
    }

    @Override
    public void activityInitData() {

        loginImmediately = (TextView) findViewById(R.id.login_immediately);
        loginImmediatelyReturn = (ImageView) findViewById(R.id.login_immediately_return);
        linearLayoutLogin = (LinearLayout) findViewById(R.id.linear_layout_login);
        linearLayoutExitLogin = (LinearLayout) findViewById(R.id.linear_layout_exit_login);
        loginName = (TextView) findViewById(R.id.login_name);
        exitLogin = (TextView) findViewById(R.id.exit_login);
        wifi = (ImageView) findViewById(R.id.wifi);
        alarmClock = (ImageView) findViewById(R.id.alarm_clock);

        loginImmediately.setOnClickListener(this);
        loginImmediatelyReturn.setOnClickListener(this);
        exitLogin.setOnClickListener(this);
        alarmClock.setOnClickListener(this);

        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        if (bmobUser == null) {
            linearLayoutLogin.setVisibility(View.VISIBLE);
            linearLayoutExitLogin.setVisibility(View.GONE);
        } else {
            linearLayoutLogin.setVisibility(View.GONE);
            linearLayoutExitLogin.setVisibility(View.VISIBLE);
            loginName.setText(bmobUser.getUsername());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_immediately://立即登录
                Intent intent = new Intent(MyApp.context, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_immediately_return://退出页面
                finish();
                break;
            case R.id.exit_login:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.mipmap.default_skin_thumbnail);
                builder.setTitle("退出登录");
                builder.setMessage("是否确认退出登录?");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUser bmobUser = new BmobUser();
                        bmobUser.logOut(MyApp.context);
                        linearLayoutLogin.setVisibility(View.VISIBLE);
                        linearLayoutExitLogin.setVisibility(View.GONE);
                        //发送广播刷新数据
                        Intent intent = new Intent(getPackageName() + ".likeSongAdd");
                        sendBroadcast(intent);
                        //发送广播更换图标
                        Intent intent2 = new Intent(getPackageName() + ".changeLogin");
                        sendBroadcast(intent2);
                    }
                });
                builder.show();
                break;
            case R.id.alarm_clock:
                timingClosure();
                break;

        }
    }

    private void timingClosure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("延时关闭程序");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        View view = LayoutInflater.from(this).inflate(R.layout.item_timing_closure, null);
        final EditText closeEt = (EditText) view.findViewById(R.id.close_app_et);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int time = Integer.valueOf(closeEt.getText().toString());
                EventBus.getDefault().post(new EventTimingClosure(time));
                Toast.makeText(LoginImmediatelyActivity.this, "将在" + time + "分钟后关闭", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }
}
