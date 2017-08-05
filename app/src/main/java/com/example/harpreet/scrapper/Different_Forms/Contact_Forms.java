package com.example.harpreet.scrapper.Different_Forms;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.specials.out.TakingOffAnimator;
import com.example.harpreet.scrapper.R;
import com.example.harpreet.scrapper.Tabs_Category.onBoarding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteck.silicompressorr.SiliCompressor;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.harpreet.scrapper.R.id.pager;


public class Contact_Forms extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Uri file;
    private Button photoUpload, panUpload, aadharUpload, submit2;
    private EditText photo, pan, aadhar, accountnumber, ifsc, contact1, contact2;
    private String panPath, aadharPath, photoPath;

    private DatabaseReference mdatabase2;

    private String con1, con2, ac, ifs, phototext, pantext, adhaartext;



    private Handler handler;
    SharedPreferences sharedpreferences;
    private AVLoadingIndicatorView avl2;


    private StorageReference mStorage;

    public static final String contactPrefs = "contactpref";

    public static final String namePrefs = "namepref";

    Bitmap photoBitmap, panBitmap, adhaarBitmap;

    public Contact_Forms() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Contact_Forms newInstance(String param1, String param2) {
        Contact_Forms fragment = new Contact_Forms();
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
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact__forms, container, false);
        final ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);

        avl2 = (AVLoadingIndicatorView) view.findViewById(R.id.avl2);
        handler = new Handler();

        photo = (EditText) view.findViewById(R.id.photo);
        pan = (EditText) view.findViewById(R.id.pan);
        aadhar = (EditText) view.findViewById(R.id.aadhar);
        contact1 = (EditText) view.findViewById(R.id.contact_1);
        contact2 = (EditText) view.findViewById(R.id.contact_2);
        accountnumber = (EditText) view.findViewById(R.id.accountNumber);
        ifsc = (EditText) view.findViewById(R.id.ifsc);
        submit2 = (Button) view.findViewById(R.id.submit2);


        photoUpload = (Button) view.findViewById(R.id.photoUpload);
        panUpload = (Button) view.findViewById(R.id.panUpload);
        aadharUpload = (Button) view.findViewById(R.id.aadharUpload);









        sharedpreferences = getActivity().getSharedPreferences(contactPrefs, Context.MODE_PRIVATE);


        //Taking permission
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            photoUpload.setEnabled(false);
            panUpload.setEnabled(false);
            aadharUpload.setEnabled(false);


            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(validation()) {
                    avl2.setVisibility(View.VISIBLE);
                    avl2.smoothToShow();



                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences prefs = getActivity().getSharedPreferences(namePrefs, MODE_PRIVATE);
                            String restoredText = prefs.getString("Name", null);
                            // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                            mdatabase2 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scrapper-189c9.firebaseio.com/User 1/" + restoredText);

                            if (restoredText != null) {
                                mStorage = FirebaseStorage.getInstance().getReference(restoredText);
                            }


                            //if (validation()) {
                            mdatabase2.child("Contact 1").setValue(con1);
                            mdatabase2.child("Contact 2").setValue(con2);
                            mdatabase2.child("Account Number").setValue(ac);
                            mdatabase2.child("IFSC").setValue(ifs);
                            try {

                                photoBitmap = SiliCompressor.with(getActivity()).getCompressBitmap(phototext, true);

                                panBitmap = SiliCompressor.with(getActivity()).getCompressBitmap(pantext, true);
                                adhaarBitmap = SiliCompressor.with(getActivity()).getCompressBitmap(aadhar.getText().toString(), true);

                                StorageReference photopath = mStorage.child("Client Photo");
                                //   photopath.putFile(Uri.parse(photo.getText().toString()));

                                uploadBitmaptofirebase(photoBitmap, photopath);


                                //    photopath.putFile()
                                StorageReference adhaarpath = mStorage.child("Client adhaar");
                                // adhaarpath.putFile(Uri.parse(aadhar.getText().toString()));

                                uploadBitmaptofirebase(adhaarBitmap, adhaarpath);


                                StorageReference panpath = mStorage.child("Client pan");

                                uploadBitmaptofirebase(panBitmap, panpath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                        clearFields(contact1, contact2, accountnumber, ifsc, pan, aadhar, photo);


                                        Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_SHORT).show();


                                        avl2.smoothToHide();


                                        pager.setCurrentItem(2, true);

                                    }

                                });


                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                            avl2.smoothToHide();


                        }
                    });

                }
            }
        });

        photoUpload.setOnClickListener(this);
        panUpload.setOnClickListener(this);
        aadharUpload.setOnClickListener(this);

        return view;
    }

    public UploadTask uploadBitmaptofirebase(Bitmap bitmap, StorageReference path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        return path.putBytes(data);


    }


    public void getFilePath(File file, int i) {

        if (i == 1) {
            pan.setText(file.getAbsolutePath());
        } else if (i == 2) {
            aadhar.setText(file.getAbsolutePath());
        } else if (i == 3) {
            photo.setText(file.getAbsolutePath());
        } else {

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                photoUpload.setEnabled(true);
                panUpload.setEnabled(true);
                aadharUpload.setEnabled(true);
            }
        }
    }

    private void takePictureByGallery(int i) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        if (i == 1) {
            startActivityForResult(intent, 40);
        } else if (i == 2) {
            startActivityForResult(intent, 50);
        } else {
            startActivityForResult(intent, 60);
        }

    }


    public Uri takePicture(int i) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        //  file = getOutputMediaFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);


        if (i == 1) {
            //pan.setText(file.toString());

            //   Toast.makeText(getActivity(), "Inside pan", Toast.LENGTH_SHORT).show();


            startActivityForResult(intent, 10);
        } else if (i == 2) {
            //   aadhar.setText(file.toString());
            startActivityForResult(intent, 20);

        } else {
            // photo.setText(file.toString());

            startActivityForResult(intent, 30);
        }

        //startActivityForResult(intent, 100);


        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {

            pan.setText(file.toString());

        } else if (requestCode == 20 && resultCode == RESULT_OK) {
            aadhar.setText(file.toString());
        } else if (requestCode == 30 && resultCode == RESULT_OK) {
            photo.setText(file.toString());
        } else if (requestCode == 40 && resultCode == RESULT_OK) {
            pan.setText(data.getData().toString());
        } else if (requestCode == 50 && resultCode == RESULT_OK) {
            aadhar.setText(data.getData().toString());
        } else if (requestCode == 60 && resultCode == RESULT_OK) {
            photo.setText(data.getData().toString());
        }


    }


    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SAPP");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    public void showDialog(final int i) {

        DialogPlus dialog = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(R.layout.dialog)).setGravity(Gravity.TOP)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {


                        if (view.getId() == R.id.Camera) {
                            Toast.makeText(getActivity(), "Inside Camera Click", Toast.LENGTH_SHORT).show();


                            takePicture(i);


                            // getFilePath(file, i);


                            // path.add(file);

                            dialog.dismiss();

                        } else if (view.getId() == R.id.Gallery) {


                            takePictureByGallery(i);
                            dialog.dismiss();

                        }

                    }


                }).setCancelable(false).setMargin(30, 30, 30, 30).setMargin(30, 30, 30, 30).setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {


                        dialogPlus.dismiss();


                    }
                }).setCancelable(true).setGravity(Gravity.CENTER).setContentBackgroundResource(Color.TRANSPARENT)
                // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();

        Toast.makeText(getActivity(), "File path is " + file, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View v) {

        Toast.makeText(getActivity(), "Path " + file, Toast.LENGTH_SHORT).show();

        if (v.getId() == R.id.panUpload) {
            showDialog(1);


            //if (getFilePath() != null)
            //   pan.setText(path.get(0).toString());

        } else if (v.getId() == R.id.aadharUpload) {
            showDialog(2);
            //if (getFilePath() != null)
//                aadhar.setText(path.get(0).toString());
        }
        if (v.getId() == R.id.photoUpload) {
            showDialog(3);
            //if (path != null)
            //              photo.setText(path.get(0).toString());
        }


    }

    public void clearFields(View... view) {

        photo.getText().clear();
        contact1.getText().clear();
        contact2.getText().clear();
        pan.getText().clear();
        aadhar.getText().clear();
        accountnumber.getText().clear();
        ifsc.getText().clear();


    }

    public boolean validation() {
        boolean valid = true;
        con1 = contact1.getText().toString();
        con2 = contact2.getText().toString();
        ac = accountnumber.getText().toString();
        ifs = ifsc.getText().toString();
        phototext = photo.getText().toString();
        pantext = pan.getText().toString();
        adhaartext = aadhar.getText().toString();

        if (TextUtils.isEmpty(con1)  ) {
            contact1.setError("Check your Contact Number");
            valid = false;

            contact1.requestFocus();

            return valid;



        }
        if(!TextUtils.isDigitsOnly(con1))
        {
            contact1.setError("Please Provide Digits only");
         //   contact1.getParent().requestChildFocus(contact1,null);

            contact1.requestFocus();

            valid = false;
            return valid;

        }
        if(con1.length() != 10)
        {
            contact1.setError("Please provide correct number");
            contact1.requestFocus();
            valid = false;
            return valid;

        }

        if (!TextUtils.isEmpty(con2)) {
            if (!TextUtils.isDigitsOnly(con2)) {
                contact2.setError("Provide Digit only");

                contact2.requestFocus();
                valid = false;
                return valid;
            }
        }  if (ac.length() < 9 || ac.length()>18 ) {
            accountnumber.setError("Please provide the correct Account Number");

            accountnumber.requestFocus();
            valid = false;
            return valid;
        }


        if( !TextUtils.isDigitsOnly(ac))
        {
            accountnumber.setError("Please provide the correct Account Number");
            accountnumber.requestFocus();
            valid = false;
            return valid;

        }

        if (ifs.length() < 11) {
            ifsc.setError("Please Check IFS code");
            valid = false;
          ifsc.requestFocus();
            return valid;
        }
        if(TextUtils.isEmpty(phototext))
        {
            photo.setError("Please provide picture");
            valid = false;
          photo.requestFocus();
            return valid;
        }
        if(TextUtils.isEmpty(pantext))
        {
            pan.setError("Please provide pan");
            pan.requestFocus();
            valid = false;
            return valid;
        }
        if(TextUtils.isEmpty(adhaartext))
        {
            aadhar.setError("Please provide aadhar");
            aadhar.requestFocus();
            valid = false;
            return valid;
        }


        return valid;
    }

}
