package com.example.administrator.surprisegiftserver.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.example.administrator.surprisegiftserver.model.Client;

import java.util.ArrayList;

/**
 * Created by Administrator on 31/5/2017.
 */

public class SpinnerClientAdapter implements SpinnerAdapter {
    ArrayList<Client> clients;
    Context context;
    public SpinnerClientAdapter(Context context,ArrayList<Client> clients)
    {
        this.clients=clients;
        this.context=context;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return clients.size();
    }

    @Override
    public Object getItem(int position) {
        return clients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
