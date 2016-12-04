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

public class ExecutiveAdapter extends BaseAdapter {

    private ArrayList<Executive> executivelist;
    private Context mContext;
    private LayoutInflater mInflater;


    public ExecutiveAdapter(Context context, ArrayList<Executive> items){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        executivelist = items;

    }
    @Override
    public int getCount() {return executivelist.size();}

    @Override
    public Object getItem(int i) {return executivelist.get(i);}

    @Override
    public long getItemId(int i) {return i;}
    public void update(ArrayList<Executive> items){
        executivelist = items;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View rowExecutive = mInflater.inflate(R.layout.executive, parent,false);
        Executive executive = executivelist.get(i);


        TextView tv = (TextView)rowExecutive.findViewById(R.id.id);
        tv.setText(String.valueOf(executive.id));

        tv = (TextView)rowExecutive.findViewById(R.id.name);
        tv.setText(executive.name);

        tv = (TextView)rowExecutive.findViewById(R.id.position);
        tv.setText(executive.position);

        return rowExecutive;

    }
}
