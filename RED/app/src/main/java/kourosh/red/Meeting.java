package kourosh.red;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kourosh on 2016-12-03.
 */

public class Meeting {
    protected String name;
    protected Date date;



    public Meeting(String name, Date date){

        this.name = name;
        this.date = date;
    }

    public static ArrayList<Meeting> getMeeting(){

        ArrayList<Meeting> result = new ArrayList<>();
        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Name,Date FROM meeting ORDER BY Date ASC");
            while(rs.next()){
                Meeting vol = new Meeting(rs.getString(1),rs.getDate(2));
                result.add(vol);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
