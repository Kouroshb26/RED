package kourosh.red;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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

public class Executives extends Fragment {
    View view;
    ExecutiveAdapter adapter;
    ListView lv;
    ArrayList<Executive> executives;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from executivess.xml
        view = inflater.inflate(R.layout.executives, container, false);


        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        executives = Executive.getExecutive();
        adapter = new ExecutiveAdapter(getContext(), executives);
        lv = (ListView) view.findViewById(R.id.lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Intent intent = new Intent(getActivity(), ExecutiveEntity.class);
                intent.putExtra("id",executives.get(position).id);
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                ArrayList<Executive> executives = Executive.getExecutive();
                adapter.update(executives);
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
                title.setText("Add ID");

                final EditText ev = (EditText) promptsView.findViewById(R.id.input);
                ev.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

                aDB
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Connection conn = MainActivity.connectionclass();
                                Statement stmt = null;
                                try {
                                    stmt = conn.createStatement();
                                    stmt.execute("INSERT INTO members(ID_no) VALUES("+ev.getText()+")");

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    stmt.execute("INSERT INTO executives(ID_no) VALUES("+ev.getText()+")");
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
    public void onResume() {
        super.onResume();
        executives = Executive.getExecutive();
        adapter.update(executives);
        lv.setAdapter(adapter);
    }

}