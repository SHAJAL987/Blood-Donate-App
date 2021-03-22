package com.example.bdl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.admin.SystemUpdateInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeBasicInfo extends AppCompatActivity {
    private static final String TAG = "Change Basic Info";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Spinner spinnerBloodGroup;
    private Spinner spinnerGender;
    private EditText userName,phoneNumber;
    private Button updateBasic;
    String date;
    String[] bloodGroup;
    String[] genderGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_basic_info);

        mDisplayDate = (TextView) findViewById(R.id.SelectDate);
        spinnerBloodGroup = (Spinner) findViewById(R.id.selectBloodgroup);
        spinnerGender = (Spinner) findViewById(R.id.selectDistrict);
        userName = (EditText) findViewById(R.id.name);
        phoneNumber = (EditText) findViewById(R.id.phone);
        updateBasic = (Button) findViewById(R.id.updateBasic);
        phoneNumber.setEnabled(false);

        updateBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    updateBasicInfo();
            }
        });



        /*Select Date from Calander*/
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        ChangeBasicInfo.this,
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
        /*Select Blood Group from spinner*/
        bloodGroup = getResources().getStringArray(R.array.bloodgroupArray);
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(this,R.layout.blood_group_layout,R.id.blood_group_id,bloodGroup);
        spinnerBloodGroup.setAdapter(groupAdapter);

        /*Select Gender*/
        genderGroup = getResources().getStringArray(R.array.genderArray);
        ArrayAdapter<String> groupAdapter1 = new ArrayAdapter<String>(this,R.layout.gender_layout,R.id.gender_group_id,genderGroup);
        spinnerGender.setAdapter(groupAdapter1);

        /********** Read data from sharedpreferance Start **********/
        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid","userid data not found ");
        String username = sharedPreferences.getString("username","username data not found ");
        String bloodgroup = sharedPreferences.getString("bloodgroup"," bloodgroup data not found ");
        String phone = sharedPreferences.getString("phone","phone data not found ");
        String gender = sharedPreferences.getString("gender","gender data not found ");
        String donatedate = sharedPreferences.getString("donatedate","donatedate  data not found ");

        /********** Set data from sharedpreferance Start **********/
        userName.setText(username);
        phoneNumber.setText(phone);
        mDisplayDate.setText(donatedate);
        int spinnerPosition = groupAdapter.getPosition(bloodgroup);
        spinnerBloodGroup.setSelection(spinnerPosition);
        int spinnerPosition1 = groupAdapter1.getPosition(gender);
        spinnerGender.setSelection(spinnerPosition1);

    }
    private void updateBasicInfo(){
        String lastdonatedate = date;
        String phone = phoneNumber.getText().toString();
        String username = userName.getText().toString();
        String sex = spinnerGender.getSelectedItem().toString();
        String bloodgroup = spinnerBloodGroup.getSelectedItem().toString();

        Call<ChangeBasicResponse> changeBasicResponseCall = ApiClient.updateBasic().updateBasicInfo(lastdonatedate,phone,username,sex,bloodgroup);
        changeBasicResponseCall.enqueue(new Callback<ChangeBasicResponse>() {
            @Override
            public void onResponse(Call<ChangeBasicResponse> call, Response<ChangeBasicResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getResult().equals("Y")){
                        new SweetAlertDialog(ChangeBasicInfo.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Updated Successfully.")
                                .show();

                        //updateStoredDate;
                        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("donatedate",date);
                        editor.putString("username",username);
                        editor.putString("bloodgroup",bloodgroup);
                        editor.putString("phone",phone);
                        editor.putString("gender",sex);
                        editor.commit();
                    }
                }
                else {
                    new SweetAlertDialog(ChangeBasicInfo.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ChangeBasicResponse> call, Throwable t) {
                new SweetAlertDialog(ChangeBasicInfo.this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Api Connection Error!")
                        .show();
            }
        });
    }

}