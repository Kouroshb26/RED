package kourosh.red;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Executives extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from executivess.xml
        View view = inflater.inflate(R.layout.executives, container, false);
        ArrayList<Executive> executives = Executive.getExecutive();

        ExecutiveAdapter adapter = new ExecutiveAdapter(getContext(),executives);
        ListView lv = (ListView) view.findViewById(R.id.lv);
        lv.setAdapter(adapter);
        return view;
    }

}