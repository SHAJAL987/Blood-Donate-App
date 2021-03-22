package com.example.bdl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class deshboard extends AppCompatActivity {

    ImageView getdonorList,updateDonateDate,sattings,about,gio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deshboard);

        getdonorList = findViewById(R.id.donorlist);
        getdonorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(deshboard.this,DonorList.class);
                startActivity(intent);
            }
        });

        updateDonateDate = findViewById(R.id.updateDonateDate);
        updateDonateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(deshboard.this,LastDonate.class);
                startActivity(intent);
            }
        });

        sattings = findViewById(R.id.sattings);
        sattings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(deshboard.this,Sattings.class);
                startActivity(intent);
            }
        });

        about = findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(deshboard.this,About.class);
                startActivity(intent);
            }
        });
        gio = findViewById(R.id.gio);
        gio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(deshboard.this,location.class);
                startActivity(intent);
            }
        });


    }
}