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

public class PresentationEntity extends AppCompatActivity {
    EditText drug;
    EditText name;
    EditText description;
    CheckBox finished;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_entity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String  drug = getIntent().getExtras().getString("drug");
        getpresentation(drug);

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
                    int state = (finished.isChecked()) ? 1 : 0;
                    stmt.executeUpdate("UPDATE presentation SET Name=\"" + name.getText() + "\", Description =\"" + description.getText() + "\", Finished =\"" + state + "\" WHERE Drug =\"" + drug.getText() + "\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();

                return true;
            case R.id.delete:
                try {
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("DELETE FROM presentation WHERE Drug =\"" + drug.getText() + "\"");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }
    protected void getpresentation(String drug){

        Connection conn = MainActivity.connectionclass();

        try {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM presentation WHERE Drug= \""+drug+"\"");
            if (rs.next()) {
                this.drug = (EditText) findViewById(R.id.id);
                this.drug.setText(drug);

                name = (EditText) findViewById(R.id.name);
                name.setText(rs.getString(2));

                description = (EditText) findViewById(R.id.description);
                description.setText(rs.getString(3));

                finished = (CheckBox) findViewById(R.id.finished);
                boolean state = rs.getString(4).equals("1");
                finished.setChecked(state);

            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

}

