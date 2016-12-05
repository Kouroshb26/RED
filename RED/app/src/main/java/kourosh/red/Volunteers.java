package kourosh.red;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Volunteers extends Fragment {
    View view;
    VolunteerAdapter adapter;
    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from volenteersxml
        view = inflater.inflate(R.layout.volunteers, container, false);

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        ArrayList<Volunteer> volunteers = Volunteer.getVolunteers();
        adapter = new VolunteerAdapter(getContext(), volunteers);
        lv = (ListView) view.findViewById(R.id.lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(),VolunteerEntity.class);
                ArrayList<Volunteer> volunteers = Volunteer.getVolunteers();
                intent.putExtra("id",volunteers.get(position).id);
                startActivity(intent);
            }

        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Volunteer> volunteers = Volunteer.getVolunteers();
                adapter.update(volunteers);
                lv.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }
    public void onResume() {
        super.onResume();
        ArrayList<Volunteer> volunteers = Volunteer.getVolunteers();
        adapter.update(volunteers);
        lv.setAdapter(adapter);
    }

}
