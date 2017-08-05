/*
package com.example.harpreet.scrapper.Different_Forms;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dd.processbutton.iml.ActionProcessButton;
import com.example.harpreet.scrapper.R;
import com.example.harpreet.scrapper.volly.MyApplication;
import com.example.harpreet.scrapper.volly.vollySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ForgotPassword extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText mobilenumber, confirmpassword, newpassword;
    private ActionProcessButton btnchangepassword;
    private String mobile, newpass, confirmpass;
    private vollySingleton volly;
    private RequestQueue queue;
    private View frameView;
    private Toolbar tool2;

    public ForgotPassword() {

    }

    */
/**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPassword.
     *//*

    // TODO: Rename and change types and number of parameters
    public static ForgotPassword newInstance(String param1, String param2) {
        ForgotPassword fragment = new ForgotPassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        volly = vollySingleton.getInstance();

        queue = vollySingleton.getInstance().getRequestQueue();

        tool2 = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tool2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.frame_include, new login()).commit();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password, container, false);
        frameView = getActivity().findViewById(R.id.frame_include);

        mobilenumber = (EditText) view.findViewById(R.id.mobile);
        newpassword = (EditText) view.findViewById(R.id.new_password);
        confirmpassword = (EditText) view.findViewById(R.id.confirm_password);

        btnchangepassword = (ActionProcessButton) view.findViewById(R.id.btnchangepassword);

        btnchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (performValidation()) {

                    performRequest(mobilenumber.getText().toString(), newpassword.getText().toString());

                }
            }
        });
        return view;
    }


    public void performRequest(final String mobile, final String pass) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getActivity(), "" + MyApplication.signupUrl, Toast.LENGTH_SHORT).show();
// Post params to be sent to the server
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("mobNum", mobile);
                params.put("newPassword", pass);


                JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, MyApplication.forgotpassword, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();

                                System.out.println(response);

                                try {
                                    boolean success = response.getBoolean("success");

                                    String message = response.getString("msg");

                                    if (success) {
                                        btnchangepassword.setProgress(100);

                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("OTP")
                                                .setContentText(message)

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {

                                                        new MaterialDialog.Builder(getActivity())
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
                        Toast.makeText(getActivity(), "Volly error" + error, Toast.LENGTH_SHORT).show();
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


                                Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();

                                System.out.println(response);

                                try {
                                    boolean success = response.getBoolean("success");

                                    String message = response.getString("msg");

                                    if (success) {
                                        btnchangepassword.setProgress(100);

                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Congratulation")
                                                .setContentText(message + "Now You Can Login ")

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {


                                                        getFragmentManager().beginTransaction().replace(frameView.getId(), new login()).commit();
                                                        sDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();

                                    } else {

                                        btnchangepassword.setProgress(100);
                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Check Your Otp")
                                                .setContentText(message )

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
                        Toast.makeText(getActivity(), "Volly error" + error, Toast.LENGTH_SHORT).show();
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

}
*/
