package com.wicoding.injaz.babycare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private IntroManger introManger;
    private TextView[] dots;
    private Button btnNext,btnSkip;
    private LinearLayout dotsLayout;
    private int[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        introManger = new IntroManger(this);
        introManger.setFirstTimeLaunch(true);
        if(!introManger.isFirstTimeLaunch())
        {
            introManger.setFirstTimeLaunch(false);
            Intent i = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(i);
            finish();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

        layouts = new int[]{
                R.layout.activity_screen_p1,
                R.layout.activity_screen_p2,
                R.layout.activity_screen_p3,
                R.layout.activity_screen_p4,
                R.layout.activity_screen_p5};

        addBottomDots(0);
        changeStatusBarColor();

        viewPageAdapter = new ViewPageAdapter();
        viewPager.setAdapter(viewPageAdapter);
        viewPager.addOnPageChangeListener(viewListener);


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = viewPager.getCurrentItem()+1;
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    Intent i = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }


    private void addBottomDots(int currentPage)
    {
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_actived);
        int[] colorInactive = getResources().getIntArray(R.array.dot_inactive);
        int[] colorScreen = getResources().getIntArray(R.array.list_screen);
        dotsLayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i] = new TextView(this);
            if (Build.VERSION.SDK_INT >= 24) {
                dots[i].setText(Html.fromHtml("&#8226;",0));

            } else {
                dots[i].setText(Html.fromHtml("&#8226;"));
            }

            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setNavigationBarColor(colorScreen[currentPage]);
        }

        if(dots.length>0)
            dots[currentPage].setTextColor(colorActive[currentPage]);
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if(position==layouts.length-1)
            {
                btnNext.setText(getString(R.string.intro_finich));
                btnSkip.setVisibility(View.GONE);
            }
            else {
                btnNext.setText(getString(R.string.intro_next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changeStatusBarColor()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPageAdapter extends PagerAdapter
    {
        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position],container,false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }
}
