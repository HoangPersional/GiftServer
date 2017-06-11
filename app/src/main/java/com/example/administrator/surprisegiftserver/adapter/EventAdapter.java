package com.example.administrator.surprisegiftserver.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.model.Event;
import com.example.administrator.surprisegiftserver.view.activity.EventDetailActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 27/5/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyView> {
    private ArrayList<Event> events;
    public static Context mContext;

    public EventAdapter(Context context, ArrayList<Event> events) {
        mContext = context;
        this.events = events;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class MyView extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        ImageView status;
        Event event;
        public MyView(final View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            status = (ImageView) itemView.findViewById(R.id.iv_status);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(itemView.getContext(), EventDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("event",event);
                    intent.putExtra("data",bundle);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Event e) {
            event=e;
            if (e.getiType() == 1)
                icon.setImageDrawable(itemView.getContext().getResources().getDrawable(R.mipmap.gift));
            name.setText(e.getName());
            switch (e.getiStatus()) {
                case 1:
                    status.setImageDrawable(itemView.getContext().getResources().getDrawable(R.mipmap.active));
                    break;
                case 2:
                    status.setImageDrawable(itemView.getContext().getResources().getDrawable(R.mipmap.to));
                    break;
                case 0:
                    status.setImageDrawable(itemView.getContext().getResources().getDrawable(R.mipmap.disable));
                    break;
            }
        }
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }
}
