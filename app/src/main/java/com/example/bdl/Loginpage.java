package com.example.bdl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginpage extends AppCompatActivity {
TextView goRegistrationPage,forgotpass;
EditText phone,pass;
Button btnlogin;

   private String id;
   private String username;
   private String bloodgroup;
   private String phoneNumber;
   private String gender;
   private String donatedate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        goRegistrationPage = (TextView)findViewById(R.id.registration);
        goRegistrationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Loginpage.this,Registration.class);
                startActivity(intent);
            }
        });
        forgotpass = (TextView)findViewById(R.id.forgotPass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Loginpage.this,ChangePassword.class);
                startActivity(intent);
            }
        });
        phone = (EditText) findViewById(R.id.phoneNumber);
        pass = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.Login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phone.getText().toString())||TextUtils.isEmpty(pass.getText().toString())){
                    new SweetAlertDialog(Loginpage.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Phone and Password must not empty.")
                            .show();
                }
                else{
                    login();
                }
            }
        });

    }

    private void login(){
        String mobile = phone.getText().toString().trim();
        String pas = pass.getText().toString().trim();
        Call<LoginResponse> loginResponseCall = ApiClient.getUserLoginService().userLogin2(mobile,pas);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                   if (response.body().getResult().equals("Y")){
                       new SweetAlertDialog(Loginpage.this,SweetAlertDialog.SUCCESS_TYPE)
                               .setTitleText("Login Successfully.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(SweetAlertDialog sweetAlertDialog) {
                               Intent intent = new Intent(Loginpage.this,deshboard.class);
                             startActivity(intent);
                               sweetAlertDialog.dismissWithAnimation();
                           }
                       }).show();

                       id = response.body().getUserid();
                       username = response.body().getUsername();
                       bloodgroup = response.body().getBloodgroup();
                       phoneNumber = response.body().getMobilenumber();
                       gender = response.body().getGender();
                       donatedate = response.body().getLastdonatedate();

                       storeData();

                   }else{
                       new SweetAlertDialog(Loginpage.this,SweetAlertDialog.WARNING_TYPE)
                               .setTitleText("Invalid credentials.")
                               .show();
                   }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                new SweetAlertDialog(Loginpage.this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("API Not Respond.")
                        .show();
            }
        });
    }
    private void storeData(){

        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userid",id);
        editor.putString("username",username);
        editor.putString("bloodgroup",bloodgroup);
        editor.putString("phone",phoneNumber);
        editor.putString("gender",gender);
        editor.putString("donatedate",donatedate);
        editor.commit();
    }


}