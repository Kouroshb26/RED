package kourosh.red;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Kourosh on 2016-12-03.
 */

public class Equipment {
    protected int id;
    protected String name;


    public Equipment(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static ArrayList<Equipment> getEquipment(){

        ArrayList<Equipment> result = new ArrayList<>();
        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_no,Name FROM equipment ORDER BY ID_no ASC");

            while(rs.next()){
                Equipment vol = new Equipment(rs.getInt(1),rs.getString(2));
                result.add(vol);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
