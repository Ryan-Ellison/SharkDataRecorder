package com.dc.shark_reel_t5;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.dc.shark_reel_t5.ui.main.Hooks;
import com.dc.shark_reel_t5.ui.main.PlaceholderFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.dc.shark_reel_t5.ui.main.SectionsPagerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import static com.dc.shark_reel_t5.ui.main.Hooks.addData;
import static com.dc.shark_reel_t5.ui.main.Hooks.getData;
import static com.dc.shark_reel_t5.ui.main.Hooks.getDataTypeLength;
import static com.dc.shark_reel_t5.ui.main.Hooks.getHookAmount;
import static com.dc.shark_reel_t5.ui.main.Hooks.getCategoriesLength;
import static com.dc.shark_reel_t5.ui.main.Hooks.getDataType;
import static com.dc.shark_reel_t5.ui.main.Hooks.hookExists;

import com.dc.shark_reel_t5.ui.main.PlaceholderFragment;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton add = findViewById(R.id.add);

        tabs.addTab(tabs.newTab());
        sectionsPagerAdapter.addHookFrag();
        addData();;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabs.addTab(tabs.newTab());
                sectionsPagerAdapter.addHookFrag();
                addData();
            }
        });


        //when click run code to export data to CSV
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //data type to store CSV, not stringBuilder is used
                //this is because it can store both commas and spaces, both critical to CSVs
                StringBuilder data = new StringBuilder();

                //add the column names from Hooks
                for (int index = 0; index < getDataTypeLength(); index++) {

                    if (index != 0) {
                        data.append(",");
                    }

                    data.append(getDataType(index));
                }

                //add the elements from each hook
                int i = 0;
                while (i == 0 || i < getHookAmount()-1) {

                    data.append("\n" + sectionsPagerAdapter.getTitle(i));
                    for (int j = 0; j < getCategoriesLength(i); j++) {

                        data.append("," + getData(i, j));
                    }

                    i++;
                }

                try {
                    //savefile into device
                    FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
                    out.write(data.toString().getBytes());
                    out.close();

                    //name file, give location, approve for exporting, and export
                    Context context = getApplicationContext();
                    File fileLocation = new File(getFilesDir(), "data.csv");
                    Uri path = FileProvider.getUriForFile(context, "com.example.exportcsv.fileprovider", fileLocation);
                    Intent fileIntent = new Intent(Intent.ACTION_SEND);
                    fileIntent.setType("text/csv");
                    fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                    fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(fileIntent, "send mail"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainActivity.this.finish();
                System.exit(0);

            }

        });


    }

}