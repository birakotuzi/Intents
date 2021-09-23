package br.edu.ifsp.scl.sdm.intents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;


import br.edu.ifsp.scl.sdm.intents.databinding.ActivityActionBinding;

public class ActionActivity extends AppCompatActivity {

    private ActivityActionBinding activityActionBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        activityActionBinding = ActivityActionBinding.inflate(getLayoutInflater());
        setContentView(activityActionBinding.getRoot());

        Toolbar toolbar = activityActionBinding.mainTb.appTb;
        toolbar.setTitle(this.getClass().getSimpleName());
        toolbar.setSubtitle(this.getIntent().getAction());

        setSupportActionBar(toolbar);


    }
}