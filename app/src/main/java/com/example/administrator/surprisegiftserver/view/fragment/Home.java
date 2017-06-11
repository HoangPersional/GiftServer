package com.example.administrator.surprisegiftserver.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.adapter.EventAdapter;
import com.example.administrator.surprisegiftserver.model.Event;
import com.example.administrator.surprisegiftserver.model.LoadEvents;
import com.example.administrator.surprisegiftserver.model.User;
import com.example.administrator.surprisegiftserver.presenter.EventListPresenter;
import com.example.administrator.surprisegiftserver.view.LoadList;
import com.example.administrator.surprisegiftserver.view.activity.AddEventActivity;
import com.example.administrator.surprisegiftserver.view.activity.EventDetailActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 22/5/2017.
 */

public class Home extends Fragment implements LoadList {
    public static int REQUEST_ADD=0;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private ArrayList<Event> events;
    private EventListPresenter eventListPresenter;
    private User user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main, container, false);
        user=getArguments().getParcelable("user");
        Log.v("ID",user.getId()+"");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        events = new ArrayList<>();
        eventAdapter = new EventAdapter(getContext(), events);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        eventListPresenter = new EventListPresenter(this, new LoadEvents(getContext(), user.getId()));
        eventListPresenter.connect();
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onLoadComplete(ArrayList<Event> events) {
        for (Event e : events
                ) {
            Log.v("HH", e.toString());
        }
        eventAdapter.setEvents(events);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.it_add:
                add();
                break;
            case R.id.it_update:
                eventListPresenter.connect();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_ADD&&resultCode==1)
        {
            eventListPresenter.connect();
        }
    }
    protected void add()
    {
        Intent intent=new Intent(getContext(), AddEventActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable("user",user);
        intent.putExtra("data",bundle);
        startActivityForResult(intent,REQUEST_ADD);
    }
}
