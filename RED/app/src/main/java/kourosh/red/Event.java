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

public class Event {
    protected String school;
    protected Date date;



    public Event(String school, Date date){
        this.school = school;
        this.date = date;
    }

    public static ArrayList<Event> getEvent(){

        ArrayList<Event> result = new ArrayList<>();
        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT School_name,Date FROM event ORDER BY School_name ASC");
            while(rs.next()){
                Event vol = new Event(rs.getString(1),rs.getDate(2));
                result.add(vol);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
