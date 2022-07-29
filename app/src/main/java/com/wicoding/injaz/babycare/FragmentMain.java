package com.wicoding.injaz.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMain extends Fragment {


    MyTextView profile_age,profile_name,profile_dob;
    CircleImageView profile_image;

    public FragmentMain() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        profile_name = (MyTextView)rootView.findViewById(R.id.profile_name);
        profile_name.setText(GlobalApp.dataUser.get_NameBaby());
        profile_dob = (MyTextView)rootView.findViewById(R.id.profile_dob);
        profile_dob.setText(GlobalApp.dataUser.get_DateOfBirth());
        profile_image = (CircleImageView)rootView.findViewById(R.id.profile_image);
        profile_image.setImageBitmap(GlobalApp.dataUser.get_Img());
        try {
            //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            //Date myDate = format.parse(GlobalApp.dataUser.get_DateOfBirth()); // date select on now milis
            profile_age = (MyTextView)rootView.findViewById(R.id.profile_age);
            profile_age.setText(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(GlobalApp.dataUser.get_DateOfBirth().split("/")[2])+" Ans");
        }catch (Exception e){Log.d("Error",e.toString());}


        return rootView;
    }


}
