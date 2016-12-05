package kourosh.red;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MeetingEntity extends AppCompatActivity {
    EditText name;
    EditText date;
    EditText location;
    EditText starttime;
    EditText endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_entity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String name = getIntent().getExtras().getString("name");
        getmeeting(name);
        ArrayList<Executive> executives = getexecs(name);
        ExecutiveAdapter adapter = new ExecutiveAdapter(this,executives);
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Connection conn = MainActivity.connectionclass();
        switch (item.getItemId()) {
            case R.id.save:
                try {
                    Statement stmt = conn.createStatement();
                    if(date.getText().equals("") || location.getText().equals("") || starttime.getText().equals("") || endtime.getText().equals("") ){
                        return true;
                    }
                    stmt.executeUpdate("UPDATE meeting SET Date =\"" + date.getText() + "\", Location =\"" + location.getText() + "\", Start_time =\"" + starttime.getText() + "\", End_time =\"" + endtime.getText() + "\" WHERE Name =\"" + name.getText() + "\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;
            case R.id.delete:
                try {
                    Statement stmt = conn.createStatement();
                    stmt.execute("DELETE FROM meeting WHERE Name=\"" + name.getText() + "\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }
    protected void getmeeting(String name){


        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Date,Location,Start_time,End_time FROM meeting WHERE Name=\""+name+"\"");
            if (rs.next()) {
                this.name = (EditText) findViewById(R.id.name);
                this.name.setText(name);

                date = (EditText) findViewById(R.id.date);
                date.setText(rs.getString(1));

                location = (EditText) findViewById(R.id.location);
                location.setText(rs.getString(2));

                starttime = (EditText) findViewById(R.id.starttime);
                starttime.setText(rs.getString(3));

                endtime = (EditText) findViewById(R.id.endtime);
                endtime.setText(rs.getString(4));
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }
    protected ArrayList<Executive> getexecs(String name){
        Connection conn = MainActivity.connectionclass();
        ArrayList<Executive> result = new ArrayList<Executive>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT a.ID_no, m.Name FROM attends AS a, members as m WHERE a.Name=\""+name+"\" AND a.ID_no = m.ID_no");
            while(rs.next()){
                result.add(new Executive(rs.getInt(1),rs.getString(2),""));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

}

