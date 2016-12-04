package kourosh.red;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Executives extends Fragment {
    View view;
    ExecutiveAdapter adapter;
    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from executivess.xml
        view = inflater.inflate(R.layout.executives, container, false);


        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        ArrayList<Executive> executives = Executive.getExecutive();
        adapter = new ExecutiveAdapter(getContext(), executives);
        lv = (ListView) view.findViewById(R.id.lv);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                ArrayList<Executive> executives = Executive.getExecutive();
                adapter.update(executives);
                lv.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }
    public void onResume() {
        super.onResume();
        ArrayList<Executive> executives = Executive.getExecutive();
        adapter.update(executives);
        lv.setAdapter(adapter);
    }

}