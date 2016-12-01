package kourosh.red;

/**
 * Created by kourosh on 2016-12-01.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Equipments extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from events.xmll
        View view = inflater.inflate(R.layout.equipments, container, false);
        return view;
    }

}