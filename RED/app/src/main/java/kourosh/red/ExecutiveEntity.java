package kourosh.red;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecutiveEntity extends AppCompatActivity {


    EditText id;
    EditText name;
    EditText email;
    EditText faculty;
    EditText joindate;
    CheckBox paid;
    EditText position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executive_entity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int id = getIntent().getExtras().getInt("id");
        getexecutive(id);

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
                    stmt.executeUpdate("UPDATE executives SET Position =\"" + position.getText() + "\" WHERE ID_no =\"" + id.getText() + "\"");
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
    protected void getexecutive(int id){
        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT v.ID_no,m.Name,m.Email,m.Faculty,m.Join_Date,m.Paid,v.Position FROM executives as v, members as m WHERE v.ID_no="+id+" AND v.ID_no = m.ID_no");
            if (rs.next()) {
                this.id = (EditText) findViewById(R.id.name);
                this.id.setText(String.valueOf(rs.getInt(1)));

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


                position = (EditText) findViewById(R.id.position);
                position.setText(rs.getString(7));
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

    }

}

