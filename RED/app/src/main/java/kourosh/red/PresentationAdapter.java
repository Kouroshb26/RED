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

public class PresentationAdapter extends BaseAdapter {

    private ArrayList<Presentation> presentationlist;
    private Context mContext;
    private LayoutInflater mInflater;


    public PresentationAdapter(Context context, ArrayList<Presentation> items){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        presentationlist = items;

    }
    @Override
    public int getCount() {return presentationlist.size();}

    @Override
    public Object getItem(int i) {return presentationlist.get(i);}

    @Override
    public long getItemId(int i) {return i;}
    public void update(ArrayList<Presentation> items){
        presentationlist = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View rowPresenation = mInflater.inflate(R.layout.presentation, parent,false);
        Presentation presentation = presentationlist.get(i);


        TextView tv = (TextView)rowPresenation.findViewById(R.id.drug);
        tv.setText(String.valueOf(presentation.drug));

        tv = (TextView)rowPresenation.findViewById(R.id.finished);
        String finish =(presentation.finished)? "Yes":"No";
        tv.setText(finish);



        return rowPresenation;

    }
}
