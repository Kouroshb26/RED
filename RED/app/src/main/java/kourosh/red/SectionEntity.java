package kourosh.red;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SectionEntity extends AppCompatActivity {

    EditText school;
    EditText date;
    EditText section;
    EditText room;
    EditText start;
    EditText end;
    Spinner presentation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_entity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String school = getIntent().getExtras().getString("school");
        String date = getIntent().getExtras().getString("date");
        String section = getIntent().getExtras().getString("section");
        getsection(school,date,section);
        presentation = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> presentations = getPresentations();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,presentations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        presentation.setAdapter(adapter);
        String currentpresentation = getcurrentpresentation(school,date,section);
        if (currentpresentation != ""){
            presentation.setSelection(adapter.getPosition(currentpresentation));
        }
    }

    private String getcurrentpresentation(String school, String date, String section) {

        String result = "";
        Connection conn = MainActivity.connectionclass();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Drug FROM section_presentation WHERE School_name =\"" + school+ "\" AND Date=\""+date+"\" AND Section_no=\""+section+"\"");
            if(rs.next()){
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ArrayList<String> getPresentations() {
        Connection conn = MainActivity.connectionclass();
        ArrayList<String> result = new ArrayList<String>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select Drug From presentation ORDER BY Drug ASC");
            while(rs.next()){
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
                    stmt.executeUpdate("UPDATE section SET Room=\"" + room.getText() + "\", Start_time =\"" + start.getText()+"\", End_time =\"" + end.getText()  + "\"WHERE School_name =\"" + school.getText()+ "\" AND Date=\""+date.getText()+"\" AND Section_no=\""+section.getText()+"\"");
                    stmt.execute("Delete FROM section_presentation WHERE School_name =\"" + school.getText()+ "\" AND Date=\""+date.getText()+"\" AND Section_no=\""+section.getText()+"\"");
                    stmt.execute("Insert INTO section_presentation VALUES(\""+presentation.getSelectedItem()+"\",\""+ school.getText() +"\",\""+ date.getText()  +"\",\""+ section.getText()+"\")");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;
            case R.id.delete:
                try {
                    Statement stmt = conn.createStatement();
                    stmt.execute("DELETE FROM section WHERE School_name =\"" + school.getText()+ "\" AND Date=\""+date.getText()+"\" AND Section_no=\""+section.getText()+"\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }
    protected void getsection(String school,String date,String section){
        Connection conn = MainActivity.connectionclass();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Room,Start_time,End_time FROM section WHERE School_name =\"" + school+ "\" AND Date=\""+date+"\" AND Section_no=\""+section+"\"");
            if (rs.next()) {
                this.school = (EditText) findViewById(R.id.school);
                this.school.setText(school);

                this.date = (EditText) findViewById(R.id.date);
                this.date.setText(date);

                this.section = (EditText) findViewById(R.id.section);
                this.section.setText(section);

                room = (EditText) findViewById(R.id.room);
                room.setText(rs.getString(1));

                start = (EditText) findViewById(R.id.start);
                start.setText(rs.getString(2));

                end = (EditText) findViewById(R.id.end);
                end.setText(rs.getString(3));
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

}

