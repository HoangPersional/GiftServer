package com.example.administrator.surprisegiftserver.view;

import com.example.administrator.surprisegiftserver.model.Event;

import java.util.ArrayList;

/**
 * Created by Administrator on 27/5/2017.
 */

public interface  LoadList {
    public  void onLoad();
    public void onLoadComplete(ArrayList<Event> events);
}
