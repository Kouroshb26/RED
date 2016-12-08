package kourosh.red;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Kourosh on 2016-12-03.
 */

public class  Executive {
    protected int id;
    protected String name;
    protected String position;


    public Executive(int id, String name, String position){
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public static ArrayList<Executive> getExecutive(){

        ArrayList<Executive> result = new ArrayList<>();
        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT e.ID_no,m.Name,e.Position FROM executives as e, members as m WHERE e.ID_no = m.ID_no ORDER BY e.ID_no ASC");
            while(rs.next()){
                Executive vol = new Executive(rs.getInt(1),rs.getString(2),rs.getString(3));
                result.add(vol);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
