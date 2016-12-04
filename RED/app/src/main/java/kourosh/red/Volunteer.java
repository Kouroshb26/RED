package kourosh.red;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kourosh on 2016-12-03.
 */

public class Volunteer {
    protected int id;
    protected String name;
    protected int totalHours;


    public Volunteer(int id,String name, int totalHours){
        this.id = id;
        this.name = name;
        this.totalHours = totalHours;
    }

    public static ArrayList<Volunteer> getVolunteers(){

        ArrayList<Volunteer> result = new ArrayList<>();
        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT v.ID_no,m.Name,v.Total_hours FROM volunteer as v, members as m WHERE v.ID_no = m.ID_no ORDER BY v.ID_no ASC");
            while(rs.next()){
                Volunteer vol = new Volunteer(rs.getInt(1),rs.getString(2),rs.getInt(3));
                result.add(vol);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
