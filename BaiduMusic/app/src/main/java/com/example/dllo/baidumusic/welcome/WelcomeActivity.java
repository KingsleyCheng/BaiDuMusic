package com.example.dllo.baidumusic.welcome;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.dllo.baidumusic.R;
import com.example.dllo.baidumusic.base.BaseActivity;
import com.example.dllo.baidumusic.db.LiteOrmSingleton;
import com.example.dllo.baidumusic.main.MainActivity;
import com.example.dllo.baidumusic.musicplayservice.PlayingListManager;
import com.example.dllo.baidumusic.songplaypage.playinglist.PlayingListBean;

/**
 * Created by dllo on 16/7/3.
 */
public class WelcomeActivity extends BaseActivity {
    private CountDownTimer timer;
    private TextView countDownTime, passTv;


    @Override
    public int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void activityInitData() {
        countDownTime = (TextView) findViewById(R.id.count_down_time);
        passTv = (TextView) findViewById(R.id.pass_tv);

        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownTime.setText("还有" + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

        passTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                timer.cancel();
                finish();
            }
        });
        PlayingListManager.getInstance().getPlayingListBeen().clear();

        for (PlayingListBean playingListBean : LiteOrmSingleton.getInstance().getLiteOrm().query(PlayingListBean.class)) {
            PlayingListManager.getInstance().getPlayingListBeen().add(playingListBean);
        }
    }

}

