package com.example.bdl;

//import com.example.bdl.response.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    /**************** Get User Service **********************/
    @GET("pls/apex/bdl_app/bdl_user/getlist/")
    Call<List<UserResponse>> getAllUsers();

    /***************** User Login Service **********************/
    @POST("pls/apex/bdl_app/bdl_user/login/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
    @FormUrlEncoded
    @POST("pls/apex/bdl_app/bdl_user/login/")
    Call<LoginResponse> userLogin2(
            @Field("mobile") String mobile,
            @Field("pass") String pass
    );

    /******************* User Registration ****************************/
    @POST("pls/apex/bdl_app/bdl_user/adduser/")
    Call<UserResponseReg> saveUser(@Body UserRequestReg userRequestReg);

    /*********** Change Donate date *************/
    @POST("pls/apex/bdl_app/bdl_user/update_donate_date")
    Call<LastDonateResponse> userdDate(@Body LastDonateRequest lastDonateRequest);
    @FormUrlEncoded
    @POST("pls/apex/bdl_app/bdl_user/update_donate_date")
    Call<LastDonateResponse> updateDate(
            @Field("lastdonatedate") String lastdonatedate,
            @Field("mobile") String mobile
    );
    /*********** Change Basic Info date *************/
    @POST("pls/apex/bdl_app/bdl_user/changeBasicInfo")
    Call<ChangeBasicResponse> updateBasic(@Body ChangeBasicRequest changeBasicRequest);
    @FormUrlEncoded
    @POST("pls/apex/bdl_app/bdl_user/changeBasicInfo")
    Call<ChangeBasicResponse> updateBasicInfo(
            @Field("lastdonatedate") String lastdonatedate,
            @Field("mobile") String mobile,
            @Field("username") String username,
            @Field("sex") String sex,
            @Field("bloodgroup") String bloodgroup
    );
    /*********** Change Password date *************/
    @POST("pls/apex/bdl_app/bdl_user/changepass")
    Call<ChangePassResponse> updatepass(@Body ChangePassRequest changePassRequest);
    @FormUrlEncoded
    @POST("pls/apex/bdl_app/bdl_user/changepass")
    Call<ChangePassResponse> Changepassword(
            @Field("mobile") String mobile,
            @Field("pass") String pass,
            @Field("oldpass") String oldpass
    );
}
