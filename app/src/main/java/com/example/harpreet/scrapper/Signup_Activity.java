package com.example.harpreet.scrapper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.example.harpreet.scrapper.volly.MyApplication;
import com.example.harpreet.scrapper.volly.vollySingleton;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Signup_Activity extends AppCompatActivity {


    // TODO: Rename and change types of parameters
    private String name, number, pass, passwordconfirm;
    private vollySingleton volly;
    private RequestQueue queue;
    ActionProcessButton btnSignIn;
    private EditText uName, mobNum, password, confirmpassword;


    private TextView logo,signin;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_signup_);
        volly = vollySingleton.getInstance();
        queue = vollySingleton.getInstance().getRequestQueue();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        progressDialog = new ProgressDialog(this);
      /*  Toolbar tool1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/
        uName = (EditText) findViewById(R.id.name);
        mobNum = (EditText) findViewById(R.id.mobilenumber);
        password = (EditText) findViewById(R.id.password);


        logo = (TextView) findViewById(R.id.shimmer_tv);

        confirmpassword = (EditText) findViewById(R.id.confirm_password);

        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                name = uName.getText().toString();
                number = mobNum.getText().toString();
                pass = password.getText().toString();
                passwordconfirm = confirmpassword.getText().toString();

                if (pass.equals(passwordconfirm)) {

                    btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
                    btnSignIn.setProgress(1);

                    publishComment(name, number, pass);
                } else {
                    confirmpassword.setError("Password doesn't match");
                }

            }     // btnSignIn.setProgress(1);

        });

        signin = (TextView) findViewById(R.id.tv_signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //   getFragmentManager().beginTransaction().replace(vv.getId(), new signup()).commit();

                startActivity(new Intent(Signup_Activity.this, LoginActivity.class));
                overridePendingTransition(R.anim.activity_in, R.anim.avtivity_out);


            }
        });

    }


    public void publishComment(final String Username, final String mobile, final String password) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                progressDialog.setMessage("Loading");
                progressDialog.show();
                Toast.makeText(Signup_Activity.this, "" + MyApplication.signupUrl, Toast.LENGTH_SHORT).show();
// Post params to be sent to the server
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", Username);
                params.put("mobNum", mobile);
                params.put("password", password);


                JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, MyApplication.signupUrl, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                //Toast.makeText(Signup_Activity.this, "" + response, Toast.LENGTH_SHORT).show();

                                //     System.out.println(response);

                                try {
                                    boolean success = response.getBoolean("success");

                                    String message = response.getString("msg");

                                    if (success) {
                                        progressDialog.dismiss();
                                        btnSignIn.setProgress(100);

                                        new SweetAlertDialog(Signup_Activity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("OTP")
                                                .setContentText(message)

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {

                                                        new MaterialDialog.Builder(Signup_Activity.this)
                                                                .title("Please enter the otp send ")
                                                                .content("We have sent you the otp!!")
                                                                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                                                                .input("Enter OTP", "Hello", new MaterialDialog.InputCallback() {
                                                                    @Override
                                                                    public void onInput(MaterialDialog dialog, CharSequence input) {


                                                                        sendOtpWithMobile(number, input.toString());
                                                                        // Toast.makeText(Signup_Activity.this, "Message " + gotSms, Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }).show();


                                                        sDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();

                                    } else {

                                        progressDialog.dismiss();
                                        btnSignIn.setProgress(100);
                                        btnSignIn.setText("Error");
                                        btnSignIn.setBackgroundColor(Color.RED);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Signup_Activity.this, "Volly error" + error, Toast.LENGTH_SHORT).show();
                        System.out.println(error);
                    }
                });

                queue.add(request_json);


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




    private void sendOtpWithMobile(final String number, final String input) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                progressDialog.setMessage("Loading");
                progressDialog.show();
// Post params to be sent to the server
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("mobNum", number);
                params.put("otp", input);

                JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, MyApplication.verifyUrl, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                Toast.makeText(Signup_Activity.this, "" + response, Toast.LENGTH_SHORT).show();

                                System.out.println(response);

                                try {
                                    boolean success = response.getBoolean("success");

                                    String message = response.getString("msg");

                                    if (success) {
                                        progressDialog.dismiss();
                                        btnSignIn.setProgress(100);

                                        new SweetAlertDialog(Signup_Activity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Congratulation")
                                                .setContentText(message + "Now You Can Login ")


                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {


                                                        //    View vv = getActivity().findViewById(R.id.frame_include);
                                                        //   getFragmentManager().beginTransaction().replace(vv.getId(), new login()).commit();

                                                        sDialog.dismissWithAnimation();
                                                        startActivity(new Intent(Signup_Activity.this, LoginActivity.class));
                                                        overridePendingTransition(R.anim.back_in, R.anim.back_out);
                                                    }
                                                })
                                                .show();

                                    } else {
                                        progressDialog.dismiss();

                                        btnSignIn.setProgress(100);
                                        btnSignIn.setText("Error");
                                        btnSignIn.setBackgroundColor(Color.RED);
                                    }

                                } catch (JSONException e) {
                                    progressDialog.dismiss();
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Signup_Activity.this, "Volly error" + error, Toast.LENGTH_SHORT).show();
                        System.out.println(error);
                    }
                });

// add the request object to the queue to be executed
                //ApplicationController.getInstance().addToRequestQueue(request_json);
                queue.add(request_json);


            }
        });


    }


}