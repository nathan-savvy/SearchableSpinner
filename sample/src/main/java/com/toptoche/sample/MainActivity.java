package com.toptoche.sample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchableSpinner spinner = (SearchableSpinner)findViewById(R.id.spinner);
        spinner.setPositiveButtonTextSize(18);
        spinner.setSearchViewBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.search_background));
        spinner.setDialogDividerColor(Color.BLACK);
        spinner.setListTypeface(Typeface.MONOSPACE);
        spinner.setPositiveButtonTextColor(Color.DKGRAY);
    }
}
