package kourosh.red;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Kourosh on 2016-12-03.
 */

public class Presentation {
    protected String drug;
    protected int finished;



    public Presentation(String drug, int finished){
        this.drug = drug;
        this.finished = finished;
    }

    public static ArrayList<Presentation> getPresentation(){

        ArrayList<Presentation> result = new ArrayList<>();
        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Drug,Finished FROM presentation ORDER BY Drug ASC");
            while(rs.next()){
                Presentation vol = new Presentation(rs.getString(1),rs.getInt(2));
                result.add(vol);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



}
