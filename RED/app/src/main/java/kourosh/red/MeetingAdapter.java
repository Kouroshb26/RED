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

public class MeetingAdapter extends BaseAdapter {

    private ArrayList<Meeting> meetinglist;
    private Context mContext;
    private LayoutInflater mInflater;


    public MeetingAdapter(Context context, ArrayList<Meeting> items){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        meetinglist = items;

    }
    @Override
    public int getCount() {return meetinglist.size();}

    @Override
    public Object getItem(int i) {return meetinglist.get(i);}

    @Override
    public long getItemId(int i) {return i;}
    public void update(ArrayList<Meeting> items){
        meetinglist = items;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View rowMeeting = mInflater.inflate(R.layout.meeting, parent,false);
        Meeting meeting = meetinglist.get(i);


        TextView tv = (TextView)rowMeeting.findViewById(R.id.name);
        tv.setText(meeting.name);

        tv = (TextView)rowMeeting.findViewById(R.id.date);
        tv.setText(String.valueOf(meeting.date));

        return rowMeeting;

    }
}
