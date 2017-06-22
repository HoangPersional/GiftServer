package com.example.administrator.surprisegiftserver.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.config.Config;

import java.util.HashMap;

/**
 * Created by Administrator on 18/6/2017.
 */

public class ImageActicity extends AppCompatActivity {
    HashMap<String,String> data;
    ImageView iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        iv=(ImageView) findViewById(R.id.iv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data= (HashMap<String, String>) getIntent().getSerializableExtra("data");
        String url=data.get("image_link");
//        Log.v("IMAGE LINK",Config.IMAGE+url);
        Glide.with(this).load(Config.IMAGE+url).error(R.mipmap.gift).into(iv);
    }
}
