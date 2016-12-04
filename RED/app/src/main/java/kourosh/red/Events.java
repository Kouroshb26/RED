package kourosh.red;

/**
 * Created by kourosh on 2016-12-01.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Events extends Fragment {
    View view;
    EventAdapter adapter;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        view = inflater.inflate(R.layout.events, container, false);



        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        ArrayList<Event> events = Event.getEvent();
        adapter = new EventAdapter(getContext(), events);
        lv = (ListView) view.findViewById(R.id.lv);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Event> events = Event.getEvent();
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
        ArrayList<Event> events = Event.getEvent();
        adapter.update(events);
        lv.setAdapter(adapter);
    }
}