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

import static kourosh.red.R.id.hours;

public class MeetingEntity extends AppCompatActivity {
    EditText name;
    EditText date;
    EditText location;
    EditText starttime;
    EditText endtime;
    ArrayList<Executive> executives;
    ExecutiveAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_entity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String name = getIntent().getExtras().getString("name");
        getmeeting(name);
        executives = getexecs(name);
        adapter = new ExecutiveAdapter(this,executives);
        lv = (ListView) findViewById(R.id.lv);

        lv.setAdapter(adapter);
        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Connection conn = MainActivity.connectionclass();

                try {
                    Executive item =  executives.get(i);
                    executives.remove(i);
                    adapter.notifyDataSetChanged();
                    Statement stmt = conn.createStatement();
                    stmt.execute("DELETE FROM attends WHERE ID_no="+item.id+" AND Name=\""+name+"\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }});

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MeetingEntity.this);
                View promptsView = li.inflate(R.layout.popup, null);

                AlertDialog.Builder aDB = new AlertDialog.Builder(MeetingEntity.this);
                aDB.setView(promptsView);

                final Spinner sp = (Spinner) promptsView.findViewById(R.id.spinner);
                ArrayList<String> executives = getallexecsadd();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MeetingEntity.this,android.R.layout.simple_spinner_item,executives);
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
                                    int hours = Integer.parseInt(String.valueOf(endtime.getText()).substring(0,2)) - Integer.parseInt(String.valueOf(starttime.getText()).substring(0,2));
                                    stmt.execute("INSERT INTO attends VALUES(\""+sp.getSelectedItem()+"\",\""+name+"\","+hours+")");
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

    @Override
    protected void onResume() {
        super.onResume();
        executives = getexecs(String.valueOf(name.getText()));
        adapter = new ExecutiveAdapter(this,executives);
        lv = (ListView) findViewById(R.id.lv);
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
    protected  ArrayList<String>  getallexecsadd(){
        Connection conn = MainActivity.connectionclass();
        ArrayList<String> result = new ArrayList<String>();

        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_no FROM executives");
            while(rs.next()){
                result.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }
    protected ArrayList<Executive> getexecs(String name){
        Connection conn = MainActivity.connectionclass();
        ArrayList<Executive> result = new ArrayList<Executive>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT a.ID_no, m.Name FROM attends as a, members as m WHERE a.Name=\""+name+"\" AND a.ID_no = m.ID_no");
            while(rs.next()){
                result.add(new Executive(rs.getInt(1),rs.getString(2),""));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

}

