package kourosh.red;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kourosh on 2016-12-03.
 */

public class EventAdapter extends BaseAdapter {

    private ArrayList<Event> eventlist;
    private Context mContext;
    private LayoutInflater mInflater;


    public EventAdapter(Context context, ArrayList<Event> items){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        eventlist = items;

    }
    @Override
    public int getCount() {return eventlist.size();}

    @Override
    public Object getItem(int i) {return eventlist.get(i);}

    @Override
    public long getItemId(int i) {return i;}
    public void update(ArrayList<Event> items){
        eventlist = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View rowEvent = mInflater.inflate(R.layout.event, parent,false);
        Event event = eventlist.get(i);


        TextView tv = (TextView)rowEvent.findViewById(R.id.schoolname);
        tv.setText(event.school);

        tv = (TextView)rowEvent.findViewById(R.id.date);
        tv.setText(String.valueOf(event.date));

        return rowEvent;

    }
}
