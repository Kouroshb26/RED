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

public class EquipmentEntity extends AppCompatActivity {

    EditText id;
    EditText name;
    EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_entity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String  id = getIntent().getExtras().getString("id");
        getequipment(id);
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
                    stmt.executeUpdate("UPDATE equipment SET Name=\"" + name.getText() + "\", Description =\"" + description.getText() + "\" WHERE ID_no =\"" + id.getText() + "\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;
            case R.id.delete:
                try {
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("DELETE FROM equipment WHERE ID_no =\"" + id.getText() + "\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }
    protected void getequipment(String id){

        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM equipment WHERE ID_no= \""+id+"\"");
            if (rs.next()) {
                this.id = (EditText) findViewById(R.id.id);
                this.id.setText(id);

                name = (EditText) findViewById(R.id.name);
                name.setText(rs.getString(2));

                description = (EditText) findViewById(R.id.description);
                description.setText(rs.getString(3));

              }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

}

