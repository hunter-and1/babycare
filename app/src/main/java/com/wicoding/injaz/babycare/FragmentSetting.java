package com.wicoding.injaz.babycare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by HunTerAnD1 on 13/05/2017.
 */

public class FragmentSetting extends Fragment {
    final int TAKEPIC = 51;
    View v;
    ImageView imView;
    ImageButton imageButtonDob,imButton;
    Button buttonSave;
    EditText editTextDob,editTextName;
    int year,month,day;
    Locale myLocale;
    LinearLayout btn_fr,btn_ar,btn_usa;
    String LangFinal = null;
    public FragmentSetting() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_setting, container, false);
        editTextDob = (EditText)v.findViewById(R.id.editTextDob);
        editTextName = (EditText)v.findViewById(R.id.editTextName);
        buttonSave  = (Button)v.findViewById(R.id.buttonSave);

        btn_fr = (LinearLayout)v.findViewById(R.id.btn_fr);
        btn_ar = (LinearLayout)v.findViewById(R.id.btn_ar);
        btn_usa = (LinearLayout)v.findViewById(R.id.btn_usa);
        LangFinal = GlobalApp.dataUser.get_LangApp();
        switch (LangFinal){
            case "fr":btn_fr.callOnClick();break;
            case "ar":btn_ar.callOnClick();break;
            case "en":btn_usa.callOnClick();break;
        }
        btn_fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LangFinal = "fr";
                btn_fr.setAlpha(1);
                btn_ar.setAlpha(0.5f);
                btn_usa.setAlpha(0.5f);
            }
        });
        btn_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LangFinal = "ar";
                btn_fr.setAlpha(0.5f);
                btn_ar.setAlpha(1);
                btn_usa.setAlpha(0.5f);
            }
        });
        btn_usa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LangFinal = "en";
                btn_fr.setAlpha(0.5f);
                btn_ar.setAlpha(0.5f);
                btn_usa.setAlpha(1);
            }
        });

        imageButtonDob = (ImageButton)v.findViewById(R.id.imageButtonDoB);
        imButton  = (ImageButton)v.findViewById(R.id.imButton);

        imageButtonDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        imButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        imView  = (ImageView) v.findViewById(R.id.imView);

        if(GlobalApp.getSharedPreferences().getBoolean("login",false))
            loadDataUser();

        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!editTextDob.getText().toString().isEmpty() && !editTextName.getText().toString().isEmpty())
                {

                    GlobalApp.dataUser.set_DateOfBirth(editTextDob.getText().toString());
                    GlobalApp.dataUser.set_NameBaby(editTextName.getText().toString());
                    GlobalApp.dataUser.set_LangApp(LangFinal);

                    GlobalApp.dataUser.set_Img(((BitmapDrawable)imView.getDrawable()).getBitmap());
                    Toast.makeText(getContext(),"Terminé",Toast.LENGTH_SHORT).show();

                    GlobalApp.setLoginSharedPreferences(getContext());
                    HomeActivity home = (HomeActivity) getActivity();
                    home.refrechData();
                    home.navigationHide(View.VISIBLE);
                    GlobalApp.setLocale(getContext(),LangFinal);
                    Intent refresh = new Intent(getActivity(), MainActivity.class);
                    startActivity(refresh);
                }
                else
                    Toast.makeText(getContext(),"Cette entrée est requise",Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }



    private void loadDataUser()
    {
        editTextDob.setText(GlobalApp.dataUser.get_DateOfBirth());
        editTextName.setText(GlobalApp.dataUser.get_NameBaby());
        imView.setImageBitmap(GlobalApp.dataUser.get_Img());
        //Lang
    }

    private void showDateDialog()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                editTextDob.setText(GlobalApp.addZero(i2,2)+"/"+ GlobalApp.addZero(i1+1,2)+"/"+ GlobalApp.addZero(i,2));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
        //Log.d("Error",System.currentTimeMillis()+"");
        //long dateNoTime = Long.parseLong((System.currentTimeMillis()+"").substring(0, (System.currentTimeMillis()+"").length() - 3));
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR)-5, 0, 1); //Before 5 Year from Now
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, TAKEPIC);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        if (requestCode == TAKEPIC && resultCode == FragmentActivity.RESULT_OK){
            Bundle b=data.getExtras();
            Bitmap img = (Bitmap)b.get("data");
            imView.setAlpha(1f);
            imView.setImageBitmap(img);
        }
    }
}
