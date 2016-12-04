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

public class Presentations extends Fragment {
    View view;
    ListView lv;
    PresentationAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        view = inflater.inflate(R.layout.presentations, container, false);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        ArrayList<Presentation> presentations = Presentation.getPresentation();
        adapter = new PresentationAdapter(getContext(), presentations);
        lv = (ListView) view.findViewById(R.id.lv);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(),VolunteerEntity.class);
                startActivity(intent);
            }

        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Presentation> presentations = Presentation.getPresentation();
                adapter.update(presentations);
                lv.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
    public void onResume() {
        super.onResume();
        ArrayList<Presentation> presentations = Presentation.getPresentation();
        adapter.update(presentations);
        lv.setAdapter(adapter);
    }

}