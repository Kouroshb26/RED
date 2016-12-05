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

public class Meetings extends Fragment {
    View view;
    ListView lv;
    MeetingAdapter adapter;
    ArrayList<Meeting> meetings;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        view = inflater.inflate(R.layout.meetings, container, false);


        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        meetings = Meeting.getMeeting();
        adapter = new MeetingAdapter(getContext(), meetings);
        lv = (ListView) view.findViewById(R.id.lv);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                Intent intent = new Intent(getActivity(), MeetingEntity.class);
                intent.putExtra("name",meetings.get(position).name);
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                meetings = Meeting.getMeeting();
                adapter.update(meetings);
                lv.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }
    public void onResume() {
        super.onResume();

        meetings = Meeting.getMeeting();
        adapter.update(meetings);
        lv.setAdapter(adapter);
    }

}