package com.example.administrator.surprisegiftserver.model;

import android.content.Context;

/**
 * Created by Administrator on 20/6/2017.
 */

public class TestTakeImage extends TestClient {
    public TestTakeImage(Context context, int idClient) {
        super(context, idClient);
        map.put("key","image");
    }
}
