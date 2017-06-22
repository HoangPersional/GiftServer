package com.example.administrator.surprisegiftserver.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.model.Client;
import com.example.administrator.surprisegiftserver.model.Demo;
import com.example.administrator.surprisegiftserver.model.Event;
import com.example.administrator.surprisegiftserver.model.LoadClients;
import com.example.administrator.surprisegiftserver.view.activity.ClientDetailActivity;
import com.example.administrator.surprisegiftserver.view.activity.EventDetailActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 19/6/2017.
 */

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyView> {
    private ArrayList<Client> clients;
    public static Context mContext;

    public ClientAdapter(Context context, ArrayList<Client> clients) {
        mContext = context;
        this.clients = clients;
    }

    @Override
    public ClientAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(ClientAdapter.MyView holder, int position) {
        holder.bind(clients.get(position));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public static class MyView extends RecyclerView.ViewHolder {
        TextView name;
        ImageView active;
        CardView cardView;
        Client client;
        Typeface typeface;

        public MyView(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name_client);
            active = (ImageView) itemView.findViewById(R.id.iv_active);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            AssetManager assetManager = itemView.getContext().getApplicationContext().getAssets();
            typeface = Typeface.createFromAsset(assetManager, "IndieFlower.ttf");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.card_view);
                    cardView.startAnimation(animation);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(itemView.getContext(), ClientDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("client", client);
                            intent.putExtra("data", bundle);
                            itemView.getContext().startActivity(intent);
                        }
                    }, 500);


                }
            });
        }

        public void bind(Client e) {
            client = e;
            name.setTypeface(typeface);
            name.setText(client.getName());
//            Log.v("NAME",client.getName());
//            active.setImageDrawable(itemView.getContext().getResources().getDrawable(R.mipmap.active));
        }
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
        notifyDataSetChanged();
    }
}
