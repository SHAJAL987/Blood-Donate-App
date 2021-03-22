package com.example.bdl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LastDonate extends AppCompatActivity {
    private static final String TAG = "LastDonate";
    private TextView mDisplayDate;
    private TextView name;
    private TextView phoneNumber;
    String date;
    Button update;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_donate);

        update = (Button) findViewById(R.id.updatedate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(date)){
                    new SweetAlertDialog(LastDonate.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Date must not empty.")
                            .show();
                }
                else{
                    update();
                }
            }
        });


        /*********** Select Date from Calander Start ******************/
        mDisplayDate = (TextView) findViewById(R.id.SelectDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        LastDonate.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy:"+dayOfMonth+"/"+month+"/"+year);
                date = dayOfMonth+"/"+month+"/"+year;
                mDisplayDate.setText(date);
            }
        };
        /*********** Select Date from Calander End ******************/

        /********** Read data from sharedpreferance Start **********/
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid","userid data not found ");
        String username = sharedPreferences.getString("username","username data not found ");
        String bloodgroup = sharedPreferences.getString("bloodgroup"," bloodgroup data not found ");
        String phone = sharedPreferences.getString("phone","phone data not found ");
        String gender = sharedPreferences.getString("gender","gender data not found ");
        String donatedate = sharedPreferences.getString("donatedate","donatedate  data not found ");

        name = (TextView)findViewById(R.id.name);
        phoneNumber = (TextView)findViewById(R.id.phone);

        name.setText(username);
        phoneNumber.setText(phone);
        mDisplayDate.setText(donatedate);


        /********** Read data from sharedpreferance End **********/

    }
    private void update(){
        String lastdonatedate = date;
        String phone = phoneNumber.getText().toString();

        Call<LastDonateResponse> lastDonateResponseCall = ApiClient.updateDonateDate().updateDate(lastdonatedate,phone);
        lastDonateResponseCall.enqueue(new Callback<LastDonateResponse>() {
            @Override
            public void onResponse(Call<LastDonateResponse> call, Response<LastDonateResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getResult().equals("Y")){
                        new SweetAlertDialog(LastDonate.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Updated Successfully.")
                                .show();
                        updateStoredDate();
                    }
                }
                else {
                    new SweetAlertDialog(LastDonate.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<LastDonateResponse> call, Throwable t) {
                new SweetAlertDialog(LastDonate.this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Api Connection Error!")
                        .show();
            }
        });
    }
    private void updateStoredDate(){
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("donatedate",date);
        editor.commit();

    }
}