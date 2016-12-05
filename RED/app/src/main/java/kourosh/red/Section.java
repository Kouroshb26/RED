package kourosh.red;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Kourosh on 2016-12-03.
 */

public class Section {
    protected String section;
    protected String room;



    public Section(String section, String room){
        this.section = section;
        this.room = room;
    }

    public static ArrayList<Section> getsections(String school, java.util.Date date){

        ArrayList<Section> result = new ArrayList<>();
        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Section_no,Room FROM section WHERE School_name=\""+school+"\" AND Date\""+String.valueOf(date)+"\"");
            while(rs.next()){
                Section vol = new Section(rs.getString(1),rs.getString(2));
                result.add(vol);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
