package com.example.administrator.surprisegiftserver.presenter;

import com.example.administrator.surprisegiftserver.model.Event;
import com.example.administrator.surprisegiftserver.model.TestConnectClient;
import com.example.administrator.surprisegiftserver.support.ConnectServer;
import com.example.administrator.surprisegiftserver.view.EventDetailView;

/**
 * Created by Administrator on 31/5/2017.
 */

public class EventDetailPresenter implements ConnectServer.ConnectComplete {
    EventDetailView eventDetailView;
    TestConnectClient testConnectClient;

    public EventDetailPresenter(EventDetailView view) {
        this.eventDetailView = view;
    }

    public EventDetailPresenter(EventDetailView view, TestConnectClient test) {
        eventDetailView = view;
        testConnectClient = test;
        testConnectClient.setConnectComplete(this);
    }

    public void connect() {
        testConnectClient.connect();
    }

    @Override
    public void response(String response) {
        eventDetailView.onTestComplete(response);
    }

    public TestConnectClient getTestConnectClient() {
        return testConnectClient;
    }

    public void setTestConnectClient(TestConnectClient testConnectClient) {
        this.testConnectClient = testConnectClient;
        this.testConnectClient.setConnectComplete(this);
    }

    public EventDetailView getEventDetailView() {
        return eventDetailView;
    }

    public void setEventDetailView(EventDetailView eventDetailView) {
        this.eventDetailView = eventDetailView;
    }
}
