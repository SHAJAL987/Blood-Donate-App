package com.example.bdl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    private static final String TAG = "Registration";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Spinner spinnerBloodGroup;
    private Spinner spinnerGender;
    private EditText userName,phone,password;
    String date;
    String[] bloodGroup;
    String[] genderGroup;

    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        /*find fields*/
        next = (Button)findViewById(R.id.signup);
        mDisplayDate = (TextView) findViewById(R.id.SelectDate);
        spinnerBloodGroup = (Spinner) findViewById(R.id.selectBloodgroup);
        spinnerGender = (Spinner) findViewById(R.id.selectDistrict);
        userName = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.spinner4);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().isEmpty()||spinnerBloodGroup.getSelectedItem().toString().isEmpty()
                        ||spinnerGender.getSelectedItem().toString().isEmpty()
                        ||phone.getText().toString().isEmpty()||date.isEmpty()
                        ||password.getText().toString().isEmpty()){
                    new SweetAlertDialog(Registration.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Field must not empty.")
                            .show();
                }else{
                    saveUser(createRequestReg());
                }


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
                        Registration.this,
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

    }
    public UserRequestReg createRequestReg(){
        UserRequestReg userRequestReg = new UserRequestReg();
        userRequestReg.setUsername(userName.getText().toString());
        userRequestReg.setBloodgroup(spinnerBloodGroup.getSelectedItem().toString());
        userRequestReg.setSex(spinnerGender.getSelectedItem().toString());
        userRequestReg.setMobile(phone.getText().toString());
        userRequestReg.setDonatedate(date);
        userRequestReg.setPass(password.getText().toString());

        return userRequestReg;
    }
    public void saveUser(UserRequestReg userRequestReg){
        Call<UserResponseReg> userResponseRegCall = ApiClient.getUserServiceReg().saveUser(userRequestReg);
        userResponseRegCall.enqueue(new Callback<UserResponseReg>() {
            @Override
            public void onResponse(Call<UserResponseReg> call, Response<UserResponseReg> response) {
                if (response.isSuccessful()){
                    if(response.body().getCount().equals("1")){
                        new SweetAlertDialog(Registration.this,SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Mobile number already exist.")
                                .show();
                    }
                    else if (response.body().getResult().equals("Y")){
                        new SweetAlertDialog(Registration.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Registered Successfully.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(Registration.this,Loginpage.class);
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).show();

                    }
                    else {

                        new SweetAlertDialog(Registration.this,SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Error.")
                                .show();
                    }


                }
                else{
                    new SweetAlertDialog(Registration.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Response Falied.")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UserResponseReg> call, Throwable t) {
                Toast.makeText(Registration.this,"Response Failed"+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}