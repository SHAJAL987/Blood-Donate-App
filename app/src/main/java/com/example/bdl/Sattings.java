package com.example.bdl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Sattings extends AppCompatActivity {

    ListView listView;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sattings);

        listView = (ListView)findViewById(R.id.listView);
        String [] values = new String[]{
                "Change basic information",
                "Change password",
                "Logout from account",
                "Privacy"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Intent intent = new Intent(Sattings.this,ChangeBasicInfo.class);
                    startActivity(intent);
                }
                if (position == 1){
                    Intent intent = new Intent(Sattings.this,ChangePassword.class);
                    startActivity(intent);
                }
                if (position == 2){
                    new SweetAlertDialog(Sattings.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure ?")
                            .setCancelText("Cancel")
                            .setConfirmText("Confirm")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(Sattings.this,Loginpage.class);
                            startActivity(intent);
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }).show();
//                    Intent intent = new Intent(Sattings.this,Loginpage.class);
//                    startActivity(intent);
                }
            }
        });



        back = (ImageView)findViewById(R.id.imageBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sattings.this,deshboard.class);
                startActivity(intent);
            }
        });


    }
}