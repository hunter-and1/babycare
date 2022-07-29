package com.wicoding.injaz.babycare;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout btnLogin,btnLastData;
    private ImageView imgAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLastData = (LinearLayout)findViewById(R.id.btnLastData);
        btnLastData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                GlobalApp.setDataUser();
                Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                i.putExtra("fragment", "main");
                startActivity(i);
                finish();
            }
        });

        if(GlobalApp.getSharedPreferences().getBoolean("login",false))
            btnLastData.setVisibility(View.VISIBLE);
        else
            btnLastData.setVisibility(View.INVISIBLE);

        imgAnimation = (ImageView)findViewById(R.id.imgAnimation);
        imgAnimation.setBackgroundResource(R.drawable.animation_baby);
        AnimationDrawable frameAnimation = (AnimationDrawable) imgAnimation.getBackground();
        frameAnimation.start();

        btnLogin = (LinearLayout)findViewById(R.id.btnConx);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                i.putExtra("fragment", "setting");
                startActivity(i);
                finish();
            }
        });
    }

}
