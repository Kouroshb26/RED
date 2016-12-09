package kourosh.red;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class EventEntity extends AppCompatActivity {
    private Context context;
    EditText school;
    EditText date;
    EditText teacher;
    EditText address;
    ArrayList<Section> sections;
    String school_name;
    Date date_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_entity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        school_name = getIntent().getExtras().getString("school");
        date_name = (Date) getIntent().getExtras().get("date");
        getevent(school_name,date_name);
        sections = Section.getsections(school_name,date_name);
        SectionAdapter adapter = new SectionAdapter(this,sections);
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,SectionEntity.class);
                intent.putExtra("school",school_name);
                intent.putExtra("date",String.valueOf(date_name));
                intent.putExtra("section",sections.get(i).section);
                startActivity(intent);
            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(EventEntity.this);
                View promptsView = li.inflate(R.layout.popupinputtext, null);

                AlertDialog.Builder aDB = new AlertDialog.Builder(EventEntity.this);
                aDB.setView(promptsView);

                final TextView title = (TextView) promptsView.findViewById(R.id.title);
                title.setText("Section Number:");

                final EditText ev = (EditText) promptsView.findViewById(R.id.input);
                ev.setInputType(InputType.TYPE_CLASS_NUMBER);

                aDB
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Connection conn = MainActivity.connectionclass();
                                try {
                                    Statement stmt = conn.createStatement();
                                    stmt.execute("INSERT INTO section(School_name,Date,Section_no) VALUES(\""+school.getText()+"\",\""+date.getText()+"\",\""+ev.getText()+"\")");

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
        sections = Section.getsections(school_name,date_name);
        SectionAdapter adapter = new SectionAdapter(this,sections);
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
                    stmt.executeUpdate("UPDATE event SET Teacher=\"" + teacher.getText() + "\", Address =\"" + address.getText() + "\"WHERE School_name =\"" + school.getText() + "\" AND Date=\""+date.getText()+"\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;
            case R.id.delete:
                try {
                    Statement stmt = conn.createStatement();
                    stmt.execute("DELETE FROM event WHERE School_name =\"" + school.getText() + "\" AND Date=\""+date.getText()+"\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }
    protected void getevent(String school,Date date){

        Connection conn = MainActivity.connectionclass();
        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Teacher,Address FROM event WHERE School_name=\""+school+"\" AND Date\""+String.valueOf(date)+"\"");
            if (rs.next()) {
                this.school = (EditText) findViewById(R.id.school);
                this.school.setText(school);

                this.date = (EditText) findViewById(R.id.date);
                this.date.setText(String.valueOf(date));

                teacher = (EditText) findViewById(R.id.teacher);
                teacher.setText(rs.getString(1));

                address = (EditText) findViewById(R.id.address);
                address.setText(rs.getString(2));
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }


}

