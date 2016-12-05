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

public class SectionAdapter extends BaseAdapter {

    private ArrayList<Section> sectionlist;
    private Context mContext;
    private LayoutInflater mInflater;


    public SectionAdapter(Context context, ArrayList<Section> items){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sectionlist = items;

    }
    @Override
    public int getCount() {return sectionlist.size();}

    @Override
    public Object getItem(int i) {return sectionlist.get(i);}

    @Override
    public long getItemId(int i) {return i;}


    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View rowSection = mInflater.inflate(R.layout.presentation, parent,false);
        Section section = sectionlist.get(i);


        TextView tv = (TextView)rowSection.findViewById(R.id.drug);
        tv.setText(section.section);

        tv = (TextView)rowSection.findViewById(R.id.finished);
        tv.setText(section.room);


        return rowSection;

    }
}
