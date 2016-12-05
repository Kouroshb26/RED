package kourosh.red;

/**
 * Created by kourosh on 2016-12-01.
 */

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

public class Events extends Fragment {
    View view;
    EventAdapter adapter;
    ListView lv;
    ArrayList<Event> events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        view = inflater.inflate(R.layout.events, container, false);



        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        events = Event.getEvent();
        adapter = new EventAdapter(getContext(), events);
        lv = (ListView) view.findViewById(R.id.lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Intent intent = new Intent(getActivity(), EventEntity.class);
                intent.putExtra("school",events.get(position).school);
                intent.putExtra("date",events.get(position).date);
                startActivity(intent);
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                events = Event.getEvent();
                adapter.update(events);
                lv.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        events = Event.getEvent();
        adapter.update(events);
        lv.setAdapter(adapter);
    }
}