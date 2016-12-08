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
import android.widget.SeekBar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VolunteerEntity extends AppCompatActivity {
    SeekBar sb;
    EditText rating;
    EditText id;
    EditText name;
    EditText email;
    EditText faculty;
    EditText joindate;
    CheckBox paid;
    EditText totalhours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_entity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        int id = getIntent().getExtras().getInt("id");
        rating = (EditText) findViewById(R.id.rating);
        sb = (SeekBar) findViewById(R.id.seekBar);
        getvolunteer(id);
        setSupportActionBar(toolbar);
        seekbar();

//        ListView lv = (ListView) findViewById(R.id.lv);
//        lv.setAdapter();




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
                    stmt.executeUpdate("UPDATE volunteer SET Rating =\"" + rating.getText() + "\" WHERE ID_no =\"" + id.getText() + "\"");
                    int state = (paid.isChecked()) ? 1 : 0;
                    stmt.executeUpdate("UPDATE members SET Name=\"" + name.getText() + "\", Email =\"" + email.getText() + "\", Faculty =\"" + faculty.getText() + "\", Join_Date =\"" + joindate.getText() + "\", Paid =\"" + state + "\" WHERE ID_no =\"" + id.getText() + "\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;
            case R.id.delete:
                try {
                    Statement stmt = conn.createStatement();
                    stmt.execute("DELETE FROM members WHERE ID_no=\"" + id.getText() + "\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    protected void getvolunteer(int id){



        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT v.ID_no,m.Name,m.Email,m.Faculty,m.Join_Date,m.Paid,v.Rating,sum(i.Hours)FROM volunteer as v, members as m,involved_in as i WHERE v.ID_no="+id+" AND v.ID_no = i.ID_no AND v.ID_no = m.ID_no");
            if (rs.next()) {
                this.id = (EditText) findViewById(R.id.id);
                this.id.setText(rs.getString(1));

                name = (EditText) findViewById(R.id.name);
                name.setText(rs.getString(2));

                email = (EditText) findViewById(R.id.email);
                email.setText(rs.getString(3));

                faculty = (EditText) findViewById(R.id.faculty);
                faculty.setText(rs.getString(4));

                joindate = (EditText) findViewById(R.id.joindate);
                joindate.setText(rs.getString(5));


                paid = (CheckBox) findViewById(R.id.paid);
                boolean state = rs.getInt(6) == 1;
                paid.setChecked(state);

                rating.setText((rs.getString(7)));
                sb.setProgress(rs.getInt(7));

                totalhours = (EditText) findViewById(R.id.totalhours);
                totalhours.setText(rs.getString(8));
            }
            rs = stmt.executeQuery("SELECT School_name,Date,Section_no FROM involved_in WHERE ID_no="+id);
            ArrayList<String> sections = new ArrayList<>();
            while (rs.next()){
               sections.add(rs.getString(1)+"   "+rs.getString(2)+"   "+rs.getString(3));
            }
            ListView lv = (ListView) findViewById(R.id.lv);
            VolunteerSectionAdapter adapter = new VolunteerSectionAdapter(this,sections);
            lv.setAdapter(adapter);


        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }


    protected void seekbar(){

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                rating.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

}

