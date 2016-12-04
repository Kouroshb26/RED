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

public class EquipmentAdapter extends BaseAdapter {

    private ArrayList<Equipment> equipmentlist;
    private Context mContext;
    private LayoutInflater mInflater;


    public EquipmentAdapter(Context context, ArrayList<Equipment> items){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        equipmentlist = items;

    }
    @Override
    public int getCount() {return equipmentlist.size();}

    @Override
    public Object getItem(int i) {return equipmentlist.get(i);}

    @Override
    public long getItemId(int i) {return i;}
    public void update(ArrayList<Equipment> items){
        equipmentlist = items;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View rowequipment = mInflater.inflate(R.layout.equipment, parent,false);
        Equipment equipment = equipmentlist.get(i);


        TextView tv = (TextView)rowequipment.findViewById(R.id.id_equipment);
        tv.setText(String.valueOf(equipment.id));

        tv = (TextView)rowequipment.findViewById(R.id.name);
        tv.setText(equipment.name);

        return rowequipment;

    }
}
