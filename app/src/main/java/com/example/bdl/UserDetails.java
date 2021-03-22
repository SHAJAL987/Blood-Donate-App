package com.example.bdl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserDetails extends AppCompatActivity {

    TextView username,bloodgroup,lastdonate,gender,mobile,prifilename;
    UserResponse userResponse;
    ImageView phoneButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        username = findViewById(R.id.userName1);
        bloodgroup = findViewById(R.id.bloodgroup);
        lastdonate = findViewById(R.id.ddate);
        gender = findViewById(R.id.gender);
        mobile = findViewById(R.id.mobile);
        prifilename = findViewById(R.id.profileName);
        phoneButton = findViewById(R.id.phoneButton);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            userResponse = (UserResponse) intent.getSerializableExtra("data");
            String usernamedata = userResponse.getUSER_NAME();
            String userBloodgroup = userResponse.getBLOOD_GROUP();
            String userLastDonateDate = userResponse.getLAST_BLOOD_DONATE();
            String userMobileNumber = userResponse.getMOBILE_NUMBER();
            String userGender = userResponse.getSEX();

            username.setText(usernamedata);
            bloodgroup.setText(userBloodgroup);
            lastdonate.setText(userLastDonateDate);
            gender.setText(userGender);
            mobile.setText(userMobileNumber);
            prifilename.setText(usernamedata);
        }
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mobile.getText().toString().trim();
                if (phone.isEmpty()){
                    new SweetAlertDialog(UserDetails.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Invalid Mobile.")
                            .show();
                }
                else {
                    String s = "tel:"+phone;
                    Intent intent1 = new Intent(Intent.ACTION_CALL);
                    intent1.setData(Uri.parse(s));
                    startActivity(intent1);
                }

            }
        });
    }
}