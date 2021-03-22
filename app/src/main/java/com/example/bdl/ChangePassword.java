package com.example.bdl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    EditText oldPassword,confirmPassword,newPassword;
    Button changeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = (EditText) findViewById(R.id.oldPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        changeBtn = (Button) findViewById(R.id.changeBtn);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPassword.getText().toString().isEmpty()||newPassword.getText().toString().isEmpty()
                        ||confirmPassword.getText().toString().isEmpty()){
                    new SweetAlertDialog(ChangePassword.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Field must not empty.")
                            .show();
                }else if(!(newPassword.getText().toString().isEmpty() == confirmPassword.getText().toString().isEmpty())){
                    new SweetAlertDialog(ChangePassword.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("New password and Confirm password must be same.")
                            .show();
                }else{
                    changePassword();
                }

            }
        });
    }
    private void changePassword(){
        String oldpass = oldPassword.getText().toString().trim();
        String newpass = newPassword.getText().toString().trim();
        String confirm = confirmPassword.getText().toString().trim();

        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String phoneNum = sharedPreferences.getString("phone","phone data not found ");

        Call<ChangePassResponse> changePassResponseCall = ApiClient.changePassword().Changepassword(phoneNum,newpass,oldpass);
        changePassResponseCall.enqueue(new Callback<ChangePassResponse>() {
            @Override
            public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getResult().equals("Y")){
                        new SweetAlertDialog(ChangePassword.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Password Changed Successfully.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(ChangePassword.this,Loginpage.class);
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).show();
                    }
                    else {
                        new SweetAlertDialog(ChangePassword.this,SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Invalid.")
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChangePassResponse> call, Throwable t) {
                new SweetAlertDialog(ChangePassword.this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("API not responding.")
                        .show();
            }
        });

    }
}