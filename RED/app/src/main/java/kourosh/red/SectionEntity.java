package kourosh.red;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
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
    ListView lv;
    SectionVolunteerAdapter adapter;
    ArrayList<Volunteer>volunteers;
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
        lv = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,presentations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        presentation.setAdapter(adapter);
        String currentpresentation = getcurrentpresentation(school,date,section);
        if (currentpresentation != ""){
            presentation.setSelection(adapter.getPosition(currentpresentation));
        }

        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Connection conn = MainActivity.connectionclass();

                try {
                    Volunteer item =  volunteers.get(i);
                    volunteers.remove(i);
                    SectionEntity.this.adapter.notifyDataSetChanged();
                    Statement stmt = conn.createStatement();
                    stmt.execute("DELETE FROM involved_in WHERE ID_no="+item.id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }});


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(SectionEntity.this);
                View promptsView = li.inflate(R.layout.popup, null);

                AlertDialog.Builder aDB = new AlertDialog.Builder(SectionEntity.this);
                aDB.setView(promptsView);

                final Spinner sp = (Spinner) promptsView.findViewById(R.id.spinner);
                ArrayList<String> volunteers = getallvolunteersadd();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SectionEntity.this,android.R.layout.simple_spinner_item,volunteers);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(adapter);

                aDB
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Connection conn = MainActivity.connectionclass();

                                try {
                                    Statement stmt = conn.createStatement();
                                    int hours = Integer.parseInt(String.valueOf(end.getText()).substring(0,2)) - Integer.parseInt(String.valueOf(start.getText()).substring(0,2));
                                    stmt.execute("INSERT INTO involved_in VALUES(\""+sp.getSelectedItem()+"\",\""+SectionEntity.this.school.getText() +"\",\""+SectionEntity.this.date.getText() +"\",\""+SectionEntity.this.section.getText() +"\","+hours+")");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                onResume();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog aD = aDB.create();
                aD.show();
            }
        });
    }

    private ArrayList<String> getallvolunteersadd() {
        Connection conn = MainActivity.connectionclass();
        ArrayList<String> volunteers = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_no FROM volunteer");
            while(rs.next()){
                volunteers.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return volunteers;
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

    protected  ArrayList<Volunteer> getvolunteers(String school,String date,String section) {
        Connection conn = MainActivity.connectionclass();
        ArrayList<Volunteer> volunteers = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT i.ID_no,m.Name FROM involved_in as i, members as m WHERE i.School_name =\"" + school + "\" AND i.Date=\"" + date + "\" AND i.Section_no=\"" + section + "\" AND i.ID_no = m.ID_no");

            while (rs.next()) {
                volunteers.add(new Volunteer(rs.getInt(1), rs.getString(2), 0));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return volunteers;
    }

    @Override
    protected void onResume() {
        super.onResume();
        volunteers = getvolunteers(String.valueOf(school.getText()),String.valueOf(date.getText()),String.valueOf(section.getText()));
        adapter = new SectionVolunteerAdapter(this,volunteers);
        lv.setAdapter(adapter);
    }

    protected void getsection(String school, String date, String section){
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

