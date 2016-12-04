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

public class VolunteerAdapter extends BaseAdapter {

    private ArrayList<Volunteer> volunteerlist;
    private Context mContext;
    private LayoutInflater mInflater;


    public VolunteerAdapter(Context context, ArrayList<Volunteer> items){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        volunteerlist = items;

    }
    @Override
    public int getCount() {return volunteerlist.size();}

    @Override
    public Object getItem(int i) {return volunteerlist.get(i);}

    @Override
    public long getItemId(int i) {return i;}
    public void update(ArrayList<Volunteer> items){
        volunteerlist = items;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View rowVolunteer = mInflater.inflate(R.layout.volunteer, parent,false);
        Volunteer volunteer = volunteerlist.get(i);


        TextView tv = (TextView)rowVolunteer.findViewById(R.id.id_volenteer);
        tv.setText(String.valueOf(volunteer.id));

        tv = (TextView)rowVolunteer.findViewById(R.id.name);
        tv.setText(volunteer.name);

        tv = (TextView)rowVolunteer.findViewById(R.id.hours);
        tv.setText(String.valueOf(volunteer.totalHours));

        return rowVolunteer;

    }
}
