package com.unlogicon.tempmail.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.androidquery.AQuery;
import com.unlogicon.tempmail.R;
import com.unlogicon.tempmail.Utils;
import com.unlogicon.tempmail.message.ActivityMessage;
import com.unlogicon.tempmail.model.Message;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by nik on 12.08.14.
 */
public class MessageAdapter extends BaseAdapter {

    private final AQuery listAq;
    Activity activity;
    ArrayList<Message> data;
    LayoutInflater layoutInflater;
    
    public MessageAdapter(Activity activity, ArrayList<Message> data) {
        this.data = data;
        this.activity = activity;
        listAq = new AQuery(activity);

        layoutInflater = activity.getLayoutInflater();
        
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item, null);
        }

        final Message message = data.get(position);
        final AQuery aq = listAq.recycle(view);
        
        aq.id(R.id.messageAutor).text(message.getMailFrom());
        aq.id(R.id.messagePreview).text(message.getMailPreview());
        aq.id(R.id.messageTime).text(Utils.getDateCurrentTimeZone(Long.valueOf(message.getMailTimestamp()).longValue()));

        aq.id(R.id.selectItem).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(activity, ActivityMessage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message", (Serializable) message);
                activity.startActivity(intent);
            }
        });
        
        return view;
    }
    
    
}
