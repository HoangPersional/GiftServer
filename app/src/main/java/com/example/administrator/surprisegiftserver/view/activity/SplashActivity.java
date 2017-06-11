package com.example.administrator.surprisegiftserver.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.adapter.Adapter;
import com.example.administrator.surprisegiftserver.view.fragment.StepOne;
import com.example.administrator.surprisegiftserver.view.fragment.StepThree;
import com.example.administrator.surprisegiftserver.view.fragment.StepTwo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17/5/2017.
 */

public class SplashActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        viewPager = (ViewPager) findViewById(R.id.vp);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StepOne());
        fragments.add(new StepTwo());
        fragments.add(new StepThree());
        Adapter adapter = new Adapter(getSupportFragmentManager(), this, fragments);
        viewPager.setAdapter(adapter);
    }
}
