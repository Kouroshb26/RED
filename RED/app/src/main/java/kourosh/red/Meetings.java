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

public class Meetings extends Fragment {
    View view;
    ListView lv;
    MeetingAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        view = inflater.inflate(R.layout.meetings, container, false);


        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        ArrayList<Meeting> meetings = Meeting.getMeeting();
        adapter = new MeetingAdapter(getContext(), meetings);
        lv = (ListView) view.findViewById(R.id.lv);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Meeting> meetings = Meeting.getMeeting();
                adapter.update(meetings);
                lv.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }
    public void onResume() {
        super.onResume();

        ArrayList<Meeting> meetings = Meeting.getMeeting();
        adapter.update(meetings);
        lv.setAdapter(adapter);
    }

}