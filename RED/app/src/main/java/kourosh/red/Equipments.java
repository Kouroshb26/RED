package kourosh.red;

/**
 * Created by kourosh on 2016-12-01.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Equipments extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        view = inflater.inflate(R.layout.equipments, container, false);

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Equipment> equipments = Equipment.getEquipment();
                EquipmentAdapter adapter = new EquipmentAdapter(getContext(),equipments);
                ListView lv = (ListView) view.findViewById(R.id.lv);
                lv.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Equipment> equipments = Equipment.getEquipment();

        EquipmentAdapter adapter = new EquipmentAdapter(getContext(),equipments);
        ListView lv = (ListView) view.findViewById(R.id.lv);
        lv.setAdapter(adapter);
    }
}