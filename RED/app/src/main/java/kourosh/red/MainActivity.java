package kourosh.red;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;

import static android.R.id.primary;

public class MainActivity extends FragmentActivity {
    private static Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);

        // Locate the viewpager in activity_main.xml
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        // Set the ViewPagerAdapter into ViewPager
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String connectionURL;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connectionURL = "jdbc:mysql://68.147.220.198:3306/cpsc471";
            connection = DriverManager.getConnection(connectionURL, "root", "aldeko16");
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

    }

    //connection class
    @SuppressLint("NewApi")
    protected static Connection connectionclass () {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String connectionURL;
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connectionURL = "jdbc:mysql://68.147.220.198:3306/cpsc471";
                connection = DriverManager.getConnection(connectionURL, "root", "aldeko16");

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        }

        return connection;
    }
}