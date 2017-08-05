/*
package com.example.harpreet.scrapper.Different_Forms;


import android.app.FragmentManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dd.processbutton.iml.ActionProcessButton;
import com.example.harpreet.scrapper.R;
import com.example.harpreet.scrapper.volly.MyApplication;
import com.example.harpreet.scrapper.volly.vollySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.example.harpreet.scrapper.volly.MyApplication.getGeoCodingApi;

*/
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signup#newInstance} factory method to
 * create an instance of this fragment.
 *//*

public class signup extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String name, number, pass, passwordconfirm;
    private vollySingleton volly;
    private RequestQueue queue;
    ActionProcessButton btnSignIn;
    private EditText uName, mobNum, password, confirmpassword;
    */
/*private SmsVerifyCatcher smsVerifyCatcher;
*//*

    private String gotSms;

    private Toolbar tool1;


    public signup() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static signup newInstance(String param1, String param2) {
        signup fragment = new signup();
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

        tool1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tool1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.frame_include, new login()).commit();

                        }
        });

       */
/* getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                if (getFragmentManager().getBackStackEntryCount() > 0) {

                    Toast.makeText(getActivity(), "yeah", Toast.LENGTH_SHORT).show();
                    getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);// show back button
                    getActivity().getActionBar().setHomeButtonEnabled(true);
                    tool1.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getFragmentManager().popBackStack();

                            Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
*//*


*/
/*
       tool1.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {





           }
       });
*//*





*/
/*
        smsVerifyCatcher = new SmsVerifyCatcher(getActivity(), new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                gotSms = code;
            }
        });
*//*



    }

*/
/*
    private String parseCode(String message) {


        Pattern p = Pattern.compile("[0-9]{1,6}");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }
*//*


*/
/*
    @Override
    public void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }
*//*


   */
/* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
*//*


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup, container, false);

        uName = (EditText) view.findViewById(R.id.name);
        mobNum = (EditText) view.findViewById(R.id.mobilenumber);
        password = (EditText) view.findViewById(R.id.password);

        confirmpassword = (EditText) view.findViewById(R.id.confirm_password);

        btnSignIn = (ActionProcessButton) view.findViewById(R.id.btnSignIn);


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


        return view;
    }

    public void publishComment(final String Username, final String mobile, final String password) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getActivity(), "" + MyApplication.signupUrl, Toast.LENGTH_SHORT).show();
// Post params to be sent to the server
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", Username);
                params.put("mobNum", mobile);
                params.put("password", password);


                JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, MyApplication.signupUrl, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();

                                System.out.println(response);

                                try {
                                    boolean success = response.getBoolean("success");

                                    String message = response.getString("msg");

                                    if (success) {
                                        btnSignIn.setProgress(100);

                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("OTP")
                                                .setContentText(message)

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {

                                                        new MaterialDialog.Builder(getActivity())
                                                                .title("Please enter the otp send ")
                                                                .content("We have send you the otp!!")
                                                                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                                                                .input("Enter OTP", "Hello", new MaterialDialog.InputCallback() {
                                                                    @Override
                                                                    public void onInput(MaterialDialog dialog, CharSequence input) {


                                                                        sendOtpWithMobile(number, input.toString());
                                                                        Toast.makeText(getActivity(), "Message " + gotSms, Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }).show();


                                                        sDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();

                                    } else {

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

                JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, MyApplication.verifyUrl, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();

                                System.out.println(response);

                                try {
                                    boolean success = response.getBoolean("success");

                                    String message = response.getString("msg");

                                    if (success) {
                                        btnSignIn.setProgress(100);

                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Congratulation")
                                                .setContentText(message + "Now You Can Login ")

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {

                                                        View vv = getActivity().findViewById(R.id.frame_include);
                                                        getFragmentManager().beginTransaction().replace(vv.getId(), new login()).commit();
                                                        sDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();

                                    } else {

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


}

*/
