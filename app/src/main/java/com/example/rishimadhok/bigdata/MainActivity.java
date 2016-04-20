package com.example.rishimadhok.bigdata;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, MyAsyncTask.MyAsyncTaskInterface {

    public ArrayList<CommodityClass> getMandiInfo(String mandiName){
        boolean refer=false;
        ArrayList<CommodityClass> ans=new ArrayList<>();
        for(int i=0;i<data.size();++i){
            if(data.get(i).market.equals(mandiName)){
                ans.add(data.get(i));
                refer=true;
            }
        }
        if(refer)
            okButton.setClickable(true);
        return ans;

    }

    private SliderLayout mDemoSlider;
    ArrayList<CommodityClass> data;
    ArrayList<CommodityClass> commodityClass;
    private ShareActionProvider mShareActionProvider;
    private Button stateChoose,mandiChoose;
    Button okButton;
//    public String[] states = {"Delhi","Punjab","Haryana","Orissa","West Bengal","Assam","Arunachal Pradesh","Gujarat","Karnataka","Uttar Pradesh"};
    public String[] states ;
//    public String[] mandi = {"Azadpur","xyz","abc"};
    ArrayList<String> mandi;
    final String appUrl = "https://play.google.com/store/apps/details?id=app.tasknearby.yashcreations.com.tasknearby";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.mipmap.fruit);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        stateChoose = (Button) findViewById(R.id.stateSelection);
        mandiChoose = (Button) findViewById(R.id.mandiSelection);

        okButton=(Button) findViewById(R.id.ok);
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Shop Wise, Be Wise",R.drawable.image1);
        file_maps.put("Naap Tol Kiya Kya!",R.drawable.image2);
        file_maps.put("Know Prices Before Shopping",R.drawable.image3);
        file_maps.put("Latest Prices At Your Fingertips", R.drawable.image4);
        file_maps.put("Eat Healthy, Stay Healthy",R.drawable.image5);

        for(String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MyAsyncTask myAsyncTask=new MyAsyncTask();
        myAsyncTask.execute();
        myAsyncTask.setMyAsyncTaskListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

//        if (id == R.id.menu_item_share) {
//            // Locate MenuItem with ShareActionProvider
//            //mShareActionProvider = (ShareActionProvider) getActionProvider();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rate) {

            Intent i = new Intent(this, RateMe.class);
            startActivity(i);

        } else if (id == R.id.nav_statistics) {

            Intent i = new Intent(this, Statistics.class);
            startActivity(i);

        } else if (id == R.id.nav_aboutus) {

            Intent i = new Intent(this, AboutUs.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            String m = "Hey!Try out the all new Sabzi Mandi app.The app is a useful utility for common man which provides details of prices of various commodities across all the markets in their state.\nVisit: " + appUrl;
            intent.setType("text/plain");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.putExtra(Intent.EXTRA_TEXT, m);
            if (intent.resolveActivity(MainActivity.this.getPackageManager()) != null)
                startActivity(intent);
            else
                Toast.makeText(MainActivity.this, "No App found to share the Details!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_contactus) {

            Intent i = new Intent(this, ContactUs.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<String> getMandiList(String stateName){
        boolean refer=false;
        ArrayList<String> ans=new ArrayList<>();
        if(data!=null)
        for(int i=0;i<data.size();++i){
            if(data.get(i).state.equals(stateName)&&!(ans.contains(data.get(i).market))){
                ans.add(data.get(i).market);
                refer=true;
            }
        }
        if(refer){
            mandiChoose.setClickable(true);
        }
        return ans;
    }



    public void stateChoose(View v)
    {
        new MaterialDialog.Builder(this)
                .title("States")
                .items(states)
                .backgroundColor(getResources().getColor(R.color.background))
                .itemsColor(getResources().getColor(R.color.text))
                .contentColor(getResources().getColor(R.color.text))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {

                        stateChoose.setText(text);
                        mandi = getMandiList((String) text);
                        if(mandi.size()==0){
                            Toast.makeText(MainActivity.this, "No  data Available for Chosen State, Choose another state", Toast.LENGTH_LONG).show();
                        }
                        for(int i=0;i<mandi.size();++i){
                            Log.i("Mandi-",mandi.get(i));
                        }
//                        startActivity(new Intent(this, EventDetail.class).putExtra("name", text));

                    }
                })
                .show();

    }

    public void mandiChoose(View v)
    {
        new MaterialDialog.Builder(this)
                .title("Mandi")
                .items(mandi)
                .backgroundColor(getResources().getColor(R.color.background))
                .itemsColor(getResources().getColor(R.color.text))
                .contentColor(getResources().getColor(R.color.text))
                .itemsCallback(new MaterialDialog.ListCallback() {

                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {

                        mandiChoose.setText(text);
                        i = new Intent(MainActivity.this, LastActivity.class);
                        i.putExtra("data",getMandiInfo(text.toString()));

//                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
//                        commodityClass = getMandiInfo((String) text);
//                        startActivity(new Intent(this, EventDetail.class).putExtra("name", text));

                    }
                })
                .show();

    }
    Intent i;
    public void ok(View v)
    {
        startActivity(i);

    }

    @Override
    public void onTaskComplete(ArrayList<CommodityClass> commodityClasses) {
        mandiChoose.setClickable(false);

        okButton.setClickable(false);
        states=new String[0];
        data=commodityClasses;
        ArrayList<String> temp=new ArrayList<>();
        if(data==null){
            Toast.makeText(MainActivity.this, "No Internet Connection, Try again after some time", Toast.LENGTH_LONG).show();
        }

        else{

            for(int i=0;i<commodityClasses.size();++i){
                if(!temp.contains(commodityClasses.get(i).state))
                    temp.add(commodityClasses.get(i).state);
            }
            states=new String[temp.size()];
            for(int i=0;i<states.length;++i){
                states[i]=temp.get(i);
            }
        }



    }
}
