package kourosh.red;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Volunteers extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from volenteersml
        View view = inflater.inflate(R.layout.volunteers, container, false);


        ArrayList<Volunteer> volunteers = Volunteer.getVolunteers();

        VolunteerAdapter adapter = new VolunteerAdapter(getContext(),volunteers);
        ListView lv = (ListView) view.findViewById(R.id.listview);
        lv.setAdapter(adapter);
        return view;
    }
}
