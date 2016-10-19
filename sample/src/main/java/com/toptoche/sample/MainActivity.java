package com.toptoche.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchableSpinner spinner = (SearchableSpinner)findViewById(R.id.spinner);
    }
}
