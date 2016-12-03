package kourosh.red;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return view;
    }

    public void onResume(){
        super.onResume();


       Connection conn = MainActivity.connectionclass();

        try {
//            List result = new ArrayList()
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID,Name,sum(Hours) FROM volunteers GROUPBY ID");
            while(rs.next()){
                rs.getInt(1);
                rs.getString(2);
                rs.getInt(3);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }



        //Set the adapter
        //create on click

    }

}
