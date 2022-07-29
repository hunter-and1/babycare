package com.wicoding.injaz.babycare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class PageActivity extends AppCompatActivity {

    WebView textDescriptionWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp); // Set the icon
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle b = getIntent().getExtras();
        int index = b.getInt("Position");
        setTitle(R.string.menu_quicktips);

        //imageHeader = (ImageView) findViewById(R.id.imageView);
        textDescriptionWeb = (WebView) findViewById(R.id.textDescriptionWeb);
        textDescriptionWeb.getSettings().setJavaScriptEnabled(true);
        textDescriptionWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        textDescriptionWeb.loadData(getString(GlobalApp.listviewDescription[index]), "text/html; charset=UTF-8", null);
    }

}
