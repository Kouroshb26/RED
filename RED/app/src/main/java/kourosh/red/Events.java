package kourosh.red;

/**
 * Created by kourosh on 2016-12-01.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Events extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        View view = inflater.inflate(R.layout.events, container, false);
        ArrayList<Event> events = Event.getEvent();

        EventAdapter adapter = new EventAdapter(getContext(),events);
        ListView lv = (ListView) view.findViewById(R.id.lv);
        lv.setAdapter(adapter);
        return view;
    }

}