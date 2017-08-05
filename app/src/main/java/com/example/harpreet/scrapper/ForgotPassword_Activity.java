package com.example.harpreet.scrapper;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dd.processbutton.iml.ActionProcessButton;
import com.example.harpreet.scrapper.Different_Forms.login;
import com.example.harpreet.scrapper.R;
import com.example.harpreet.scrapper.volly.MyApplication;
import com.example.harpreet.scrapper.volly.vollySingleton;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ForgotPassword_Activity extends AppCompatActivity {

    private EditText mobilenumber, confirmpassword, newpassword;
    private ActionProcessButton btnchangepassword;
    private String mobile, newpass, confirmpass;
    private vollySingleton volly;
    private RequestQueue queue;


    private TextView signin;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password_);
        volly = vollySingleton.getInstance();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        queue = vollySingleton.getInstance().getRequestQueue();

/*
        Toolbar tool1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/

        mobilenumber = (EditText) findViewById(R.id.mobile);
        newpassword = (EditText) findViewById(R.id.new_password);
        confirmpassword = (EditText) findViewById(R.id.confirm_password);

        btnchangepassword = (ActionProcessButton) findViewById(R.id.btnchangepassword);

        signin = (TextView) findViewById(R.id.tv_signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //   getFragmentManager().beginTransaction().replace(vv.getId(), new signup()).commit();

                startActivity(new Intent(ForgotPassword_Activity.this, Signup_Activity.class));
                overridePendingTransition(R.anim.activity_in, R.anim.avtivity_out);


            }
        });



        btnchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (performValidation()) {

                    performRequest(mobilenumber.getText().toString(), newpassword.getText().toString());

                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_in, R.anim.back_out);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==android.R.id.home)
        {
            Intent i= new Intent(this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            overridePendingTransition(R.anim.back_in, R.anim.back_out);

            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void performRequest(final String mobile, final String pass) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(ForgotPassword_Activity.this, "" + MyApplication.signupUrl, Toast.LENGTH_SHORT).show();
// Post params to be sent to the server
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("mobNum", mobile);
                params.put("newPassword", pass);


                JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, MyApplication.forgotpassword, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                Toast.makeText(ForgotPassword_Activity.this, "" + response, Toast.LENGTH_SHORT).show();

                                System.out.println(response);

                                try {
                                    boolean success = response.getBoolean("success");

                                    String message = response.getString("msg");

                                    if (success) {
                                        btnchangepassword.setProgress(100);

                                        new SweetAlertDialog(ForgotPassword_Activity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("OTP")
                                                .setContentText(message)

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {

                                                        new MaterialDialog.Builder(ForgotPassword_Activity.this)
                                                                .title("Please enter the otp send ")
                                                                .content("We have sent you the otp!!")
                                                                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                                                                .input("Enter OTP", "", new MaterialDialog.InputCallback() {
                                                                    @Override
                                                                    public void onInput(MaterialDialog dialog, CharSequence input) {


                                                                        System.out.println(input.toString());
                                                                        sendOtpWithMobile(mobile, input.toString());
                                                                        // Toast.makeText(getActivity(), "Message " + gotSms, Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }).show();


                                                        sDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();

                                    } else {

                                        btnchangepassword.setProgress(100);
                                        btnchangepassword.setText("Error");
                                        btnchangepassword.setBackgroundColor(Color.RED);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgotPassword_Activity.this, "Volly error" + error, Toast.LENGTH_SHORT).show();
                        System.out.println(error);
                    }
                });

// add the request object to the queue to be executed
                //ApplicationController.getInstance().addToRequestQueue(request_json);
                queue.add(request_json);


            }
        });
    }


    private void sendOtpWithMobile(final String number, final String input) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {


// Post params to be sent to the server
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("mobNum", number);
                params.put("otp", input);

                JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, MyApplication.verifyforgototp, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                Toast.makeText(ForgotPassword_Activity.this, "" + response, Toast.LENGTH_SHORT).show();

                                System.out.println(response);

                                try {
                                    boolean success = response.getBoolean("success");

                                    String message = response.getString("msg");

                                    if (success) {
                                        btnchangepassword.setProgress(100);

                                        new SweetAlertDialog(ForgotPassword_Activity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Congratulation")
                                                .setContentText(message + "Now You Can Login ")

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {


                                                      //  getFragmentManager().beginTransaction().replace(frameView.getId(), new login()).commit();

                                                       startActivity(new Intent(ForgotPassword_Activity.this,LoginActivity.class));
                                                        sDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();

                                    } else {

                                        btnchangepassword.setProgress(100);
                                        new SweetAlertDialog(ForgotPassword_Activity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Check Your Otp")
                                                .setContentText(message)

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {


                                                        //   getFragmentManager().beginTransaction().replace(frameView.getId(), new login()).commit();
                                                        btnchangepassword.setText("Change Password");
                                                        btnchangepassword.setBackgroundColor(Color.RED);

                                                        sDialog.dismissWithAnimation();


                                                    }
                                                })
                                                .show();


                                        btnchangepassword.setText("Check Credentials");
                                        btnchangepassword.setBackgroundColor(Color.RED);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgotPassword_Activity.this, "Volly error" + error, Toast.LENGTH_SHORT).show();
                        System.out.println(error);
                    }
                });

// add the request object to the queue to be executed
                //ApplicationController.getInstance().addToRequestQueue(request_json);
                queue.add(request_json);


            }
        });


    }


    public boolean performValidation() {

        boolean fine = true;
        mobile = mobilenumber.getText().toString();
        newpass = newpassword.getText().toString();
        confirmpass = confirmpassword.getText().toString();

        if (TextUtils.isEmpty(mobile)) {

            mobilenumber.setError("Empty mobile");
            fine = false;
            return fine;

        }
        if (!TextUtils.isDigitsOnly(mobile)) {
            mobilenumber.setError("Only Digits are allowed");

            fine = false;
            return fine;

        }

        if (TextUtils.isEmpty(newpass)) {

            newpassword.setError("Type your new password");
            fine = false;
            return fine;

        }

        if (TextUtils.isEmpty(confirmpass)) {

            newpassword.setError("This Shouldn't be empty");
            fine = false;
            return fine;

        }

        if (!newpass.equals(confirmpass)) {
            confirmpassword.setError("Password Doesn't match");

            fine = false;
            return fine;
        }

        return fine;
    }

    public void backToLogin(View view) {

        Intent i= new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }
}