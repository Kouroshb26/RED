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

public class Presentations extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        View view = inflater.inflate(R.layout.presentations, container, false);
        ArrayList<Presentation> presentations = Presentation.getPresentation();

        PresentationAdapter adapter = new PresentationAdapter(getContext(),presentations);
        ListView lv = (ListView) view.findViewById(R.id.lv);
        lv.setAdapter(adapter);
        return view;
    }

}