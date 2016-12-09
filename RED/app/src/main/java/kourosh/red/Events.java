package kourosh.red;

/**
 * Created by kourosh on 2016-12-01.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.popupinputtext, null);

                AlertDialog.Builder aDB = new AlertDialog.Builder(getContext());
                aDB.setView(promptsView);

                final TextView title = (TextView) promptsView.findViewById(R.id.title);
                title.setText("Name and Date:");

                final EditText ev = (EditText) promptsView.findViewById(R.id.input);
                ev.setInputType(InputType.TYPE_CLASS_TEXT);
                final EditText ev2 = (EditText) promptsView.findViewById(R.id.input2);
                ev2.setVisibility(View.VISIBLE);
                ev2.setHint("YYYY-MM-DD");

                aDB
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Connection conn = MainActivity.connectionclass();
                                try {
                                    Statement stmt = conn.createStatement();
                                    stmt.execute("INSERT INTO event(School_name,Date) VALUES(\""+ev.getText()+"\",\""+ev2.getText()+"\")");

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                onResume();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog aD = aDB.create();
                aD.show();
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