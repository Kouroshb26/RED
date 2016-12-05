package kourosh.red;

/**
 * Created by kourosh on 2016-12-01.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Equipments extends Fragment {
    View view;
    ListView lv;
    EquipmentAdapter adapter;
    ArrayList<Equipment> equipments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        view = inflater.inflate(R.layout.equipments, container, false);

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        equipments = Equipment.getEquipment();
        adapter = new EquipmentAdapter(getContext(), equipments);
        lv = (ListView) view.findViewById(R.id.lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(),EquipmentEntity.class);
                intent.putExtra("id",String.valueOf(equipments.get(position).id));
                startActivity(intent);
            }

        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public void onRefresh() {
                equipments = Equipment.getEquipment();
                adapter.update(equipments);
                lv.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        equipments = Equipment.getEquipment();
        adapter.update(equipments);
        lv.setAdapter(adapter);
    }
}