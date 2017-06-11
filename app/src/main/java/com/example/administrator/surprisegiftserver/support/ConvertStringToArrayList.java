package com.example.administrator.surprisegiftserver.support;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 12/23/2016.
 */

public class ConvertStringToArrayList {
    public static <T> ArrayList<T> convertStringToArr(String json, Class<T> type) {
        ArrayList<T> arrayList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            arrayList = gson.fromJson(json, new ListOfSomething<T>(type));
        } catch (Exception e) {
            Log.v("HH", "mistake " + json);
        }
        return arrayList;
    }
    static class ListOfSomething<X> implements ParameterizedType {

        private Class<?> wrapped;

        public ListOfSomething(Class<X> wrapped) {
            this.wrapped = wrapped;
        }

        public Type[] getActualTypeArguments() {
            return new Type[] {wrapped};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }

    }
}
