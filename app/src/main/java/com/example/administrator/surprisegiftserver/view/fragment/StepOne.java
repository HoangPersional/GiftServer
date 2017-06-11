package com.example.administrator.surprisegiftserver.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.surprisegiftserver.R;

/**
 * Created by Administrator on 17/5/2017.
 */

public class StepOne extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.step_one,container,false);
        return view;
    }
}
