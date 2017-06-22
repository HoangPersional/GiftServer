package com.example.administrator.surprisegiftserver.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.adapter.ClientAdapter;
import com.example.administrator.surprisegiftserver.model.Client;
import com.example.administrator.surprisegiftserver.model.LoadClients;
import com.example.administrator.surprisegiftserver.model.User;
import com.example.administrator.surprisegiftserver.presenter.AddEventPresenter;
import com.example.administrator.surprisegiftserver.support.GridSpacingItemDecoration;
import com.example.administrator.surprisegiftserver.view.AddEventView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 19/6/2017.
 */

public class ClientManager extends Fragment implements AddEventView {
    RecyclerView recyclerView;
    ArrayList<Client> clients;
    ClientAdapter clientAdapter;
    AddEventPresenter addEventPresenter;
    LoadClients loadClients;
    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_managerment, container, false);
        user = getArguments().getParcelable("user");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        clients = new ArrayList<>();
        clientAdapter = new ClientAdapter(getContext(), clients);
        recyclerView.setAdapter(clientAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(5, 5, true));
        addEventPresenter = new AddEventPresenter(this);
        onGetClient(user);

    }

    @Override
    public void onReload() {

    }

    @Override
    public void onClient(boolean b) {

    }

    @Override
    public void push() {

    }

    @Override
    public void onPushSuccess(boolean b) {

    }

    @Override
    public void onGetClient(User user) {
        loadClients = new LoadClients(getContext(), user);
        addEventPresenter.setLoadClients(loadClients);
        addEventPresenter.loadClient();
    }

    @Override
    public void onGetClientComplete(ArrayList<Client> clients) {
        Log.v("HH", clients.size() + "");
        clientAdapter.setClients(clients);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        menu.findItem(R.id.it_add).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.it_update)
        {
            onGetClient(user);
        }
        return true;
    }
}
