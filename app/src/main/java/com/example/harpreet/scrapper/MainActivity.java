package com.example.harpreet.scrapper;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.harpreet.scrapper.Tabs_Category.RateCard;
import com.example.harpreet.scrapper.Tabs_Category.Transactions;
import com.example.harpreet.scrapper.Tabs_Category.onBoarding;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager pager;
    private TabLayout tabs;
    AHBottomNavigation bottomNavigation;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (Build.VERSION.SDK_INT > 21) {
            Slide slide = new Slide();
            slide.setDuration(500);
            getWindow().setEnterTransition(slide);

        }*/
       /* if (Build.VERSION.SDK_INT > 21) {
            Explode slide = new Explode();
            slide.setDuration(500);
            getWindow().setExitTransition(slide);

        }
*/

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler = new Handler();
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //   setupWindowAnimations();



/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
       /* BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.boarding:
                        selectedFragment = new onBoarding();
                        //     setMainTitle("OKHLEE");


                        //  mTextMessage.setText(R.string.title_home);
                        break;
                    case R.id.transaction:
                        selectedFragment = new Transactions();
                        //mTextMessage.setText(R.string.title_dashboard);
                        //   setMainTitle("Buy");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selectedFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new onBoarding()).commit();
*/
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("RATE CARD", R.drawable.ratecard);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("TRANSACTION", R.drawable.transactions);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("PROFILE", R.drawable.profile);




        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setAccentColor(Color.parseColor("#075e54"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setCurrentItem(0);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(final int position, boolean wasSelected) {

                Fragment selectedFragment = null;
                switch (position) {
                    case 0:
                        selectedFragment= RateCard.newInstance("","");
                     //   selectedFragment = new onBoarding();
                        break;
                   case 1:
                       // selectedFragment= RateCard.newInstance("","");
                       bottomNavigation.setEnabled(false);
                        selectedFragment= null;
                        break;
                    case 2:
                        selectedFragment = new onBoarding();
                        // selectedFragment= RateCard.newInstance("","");
                }
                if(selectedFragment!=null)
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selectedFragment).commit();

                return true;

            }

        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new RateCard()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

            this.finish();

        } else {
            super.onBackPressed();
        }*/

        handler.post(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Exit Application?");
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        moveTaskToBack(true);
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                        System.exit(1);
                                    }
                                })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

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
        if (id == R.id.logout) {
            Intent in = new Intent(this, Gps_service.class);

            Toast.makeText(this, "Location service has been stopped.", Toast.LENGTH_SHORT).show();
            stopService(in);

            handler.post(new Runnable() {
                @Override
                public void run() {

                   /* startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    overridePendingTransition(R.anim.back_in, R.anim.back_out);*/

                }
            });
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }








    /*class myAdapter extends FragmentStatePagerAdapter {


        public myAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            if (position == 0) {
                return new onBoarding();
                //  return null;
            } else if (position==1)

            {
                //return new category();
                //return null;
                return new Transactions();
            }
            else return null;


        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "OnBoarding";
            } else if (position==1){
                return "Transactions";
            }
            else return null;



        }


    }*/


}


