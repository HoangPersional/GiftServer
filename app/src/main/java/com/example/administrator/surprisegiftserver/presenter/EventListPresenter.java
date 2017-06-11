package com.example.administrator.surprisegiftserver.presenter;

import android.util.Log;

import com.example.administrator.surprisegiftserver.model.Event;
import com.example.administrator.surprisegiftserver.model.EventDbManager;
import com.example.administrator.surprisegiftserver.model.LoadEvents;
import com.example.administrator.surprisegiftserver.support.ConnectServer;
import com.example.administrator.surprisegiftserver.support.ConvertStringToArrayList;
import com.example.administrator.surprisegiftserver.view.LoadList;

import java.util.ArrayList;

/**
 * Created by Administrator on 27/5/2017.
 */

public class EventListPresenter implements ConnectServer.ConnectComplete {
    private LoadList view;
    private LoadEvents loadEvents;
    private ArrayList<Event> events;
    private EventDbManager eventDbManager;
    public EventListPresenter(LoadList view, LoadEvents events) {
        this.view = view;
        this.loadEvents = events;
        eventDbManager=EventDbManager.getInstance(events.getContext());
    }
    public void connect()
    {
        loadEvents.setConnectComplete(this);
        loadEvents.connect();
    }
    @Override
    public void response(String response) {
        events= ConvertStringToArrayList.convertStringToArr(response,Event.class);
        view.onLoadComplete(events);
        eventDbManager.clear();
        eventDbManager.insertEvents(events);
    }
}
