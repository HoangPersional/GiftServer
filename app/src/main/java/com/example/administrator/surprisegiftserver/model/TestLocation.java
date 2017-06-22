package com.example.administrator.surprisegiftserver.model;

import android.content.Context;

/**
 * Created by Administrator on 20/6/2017.
 */

public class TestLocation extends TestClient {
    public TestLocation(Context context, int idClient) {
        super(context, idClient);
        map.put("key","location");
    }
}
