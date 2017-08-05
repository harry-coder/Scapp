package com.example.harpreet.scrapper.Different_Forms;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.harpreet.scrapper.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cost_Camera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cost_Camera extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText source;
    private ImageView upload;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Uri file;


    public Cost_Camera() {
        // Required empty public constructor
    }


    public static Cost_Camera newInstance(String param1, String param2) {
        Cost_Camera fragment = new Cost_Camera();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.cost__camera, container, false);

        source= (EditText) view.findViewById(R.id.imageSource);
        upload= (ImageView) view.findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showDialog();


            }
        });


        //Taking the permission
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            upload.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }




        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                upload.setEnabled(true);
            }
        }
    }
    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);


        Toast.makeText(getActivity(), ""+file, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                source.setText(file.toString());
            }
        }
    }



    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"SAPP");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    public void showDialog()
    {
      DialogPlus dialog = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(R.layout.dialog)).setGravity(Gravity.TOP)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {



                        if (view.getId() == R.id.Camera)

                        {
                            Toast.makeText(getActivity(), "Inside Camera Click", Toast.LENGTH_SHORT).show();


                            takePicture();


                        }

                        dialog.dismiss();

                        if (view.getId() == R.id.Gallery)
                        {




                        }

                    }


                }).setCancelable(false).setMargin(30, 30, 30, 30).setMargin(30,30, 30, 30).setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {


                        dialogPlus.dismiss();



                    }
                }).setCancelable(true).setGravity(Gravity.CENTER).setContentBackgroundResource(Color.TRANSPARENT)
                // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();





    }

}
