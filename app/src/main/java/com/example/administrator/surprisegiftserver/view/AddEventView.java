package com.example.administrator.surprisegiftserver.view;

import com.example.administrator.surprisegiftserver.model.Client;
import com.example.administrator.surprisegiftserver.model.User;

import java.util.ArrayList;

/**
 * Created by Administrator on 23/5/2017.
 */

public interface AddEventView {
    void onReload();
    void onClient(boolean b);
    void push();
    void onPushSuccess(boolean b);
    void onGetClient(User user);
    void onGetClientComplete(ArrayList<Client> clients);
}
