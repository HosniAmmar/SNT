package com.img.snt;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.img.snt.Adapter.HTTPDataHandler;
import com.img.snt.DAO.AppDatabase;
import com.img.snt.Model.Item;
import com.img.snt.Model.RSSObject;
import com.img.snt.Model.Source;
import com.img.snt.ui.main.PlaceholderFragment;
import com.img.snt.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private final Source[] RSS_link={
            new Source("webmanagercenter","http://ar.webmanagercenter.com/feed/?cat=10","AR","Actu","News",10,1),
            new Source("webmanagercenter","http://www.webmanagercenter.com/feed/?cat=34","FR","Actu","News",10,1),
            new Source("Leaders","https://www.leaders.com.tn/rss","FR","Actu","News",10,1),
            new Source("Mosaique FM","https://www.mosaiquefm.net/fr/rss","FR","Actu","News",10,1),
            new Source("Mosaique FM","https://www.mosaiquefm.net/ar/rss","AR","Actu","News",10,1),
            new Source("MBusinessnews","https://www.businessnews.com.tn/rss.xml","FR","Actu","News",10,1)


    };
    static SharedPreferences settings;
    static SharedPreferences.Editor editor;
    public static String language="";
    public static int languageIndex=0;
    public final  String[] languages={
            "FR",
            "EN",
            "AR"
    };
    private final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json?rss_url=";
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    Button languageBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = this.getPreferences(MODE_PRIVATE);
        languageBtn =  findViewById(R.id.languageBtn);
        languageBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        languageIndex++;
                        if(languageIndex==languages.length)
                            languageIndex=0;
                        languageBtn.setText(languages[languageIndex]);
                        language =languages[languageIndex];
                        editor = settings.edit();
                        editor.putInt("LanguageIndex",languageIndex);

                        editor.commit();
                        refreshFragment();
                    }
                }
        );
       // editor = settings.edit();
        //editor.putString("language", "1");

        //editor.commit();


        languageIndex = settings.getInt("LanguageIndex", 0);
        languageBtn.setText(languages[languageIndex]);
        language =languages[languageIndex];
                loadAllRss();
        //DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(this));
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action" +language , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                loadAllRss();
            }
        });



    }
    private void loadAllRss(){
        for(int i=0;i<RSS_link.length;i++){
            loadRSS( RSS_link[i].sourceUrl,i);
        }
    }
    private void loadRSS(String RSSLink, final int sourceIndex) {
        @SuppressLint("StaticFieldLeak") AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() {

            ProgressDialog mDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Please wait...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return  result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                RSSObject newrssObject = new Gson().fromJson(s,RSSObject.class);
                if(newrssObject!=null){


                newrssObject.items.size();
                for(int i=0;i<newrssObject.items.size();i++){
                    if(!newrssObject.items.get(i).thumbnail.contains("https")){
                        if(newrssObject.items.get(i).thumbnail.contains("http")){
                            newrssObject.items.get(i).thumbnail.replaceFirst("http","https");

                        }

                    }
                   Item item =new Item(newrssObject.items.get(i).guid,newrssObject.items.get(i).title,newrssObject.items.get(i).content,newrssObject.items.get(i).pubDate,
                           RSS_link[sourceIndex].sourceName,RSS_link[sourceIndex].language,RSS_link[sourceIndex].category,RSS_link[sourceIndex].type,RSS_link[sourceIndex].priority,newrssObject.items.get(i).thumbnail);
                   if(AppDatabase.getAppDatabase(MainActivity.this).itemDAO().countId(item.id)==0){
                       DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(MainActivity.this),item);
                       PlaceholderFragment.mAdapter.notifyDataSetChanged();
                       // adapter.notifyDataSetChanged();
                       sectionsPagerAdapter = new SectionsPagerAdapter(MainActivity.this, getSupportFragmentManager());
                       viewPager = findViewById(R.id.view_pager);
                       viewPager.setAdapter(sectionsPagerAdapter);
                   }

                }
                //for()
                }
            }
        };
        // for(int i=0;i<RSS_link.length;i++){


        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_API);
        url_get_data.append(RSSLink);
        loadRSSAsync.execute(url_get_data.toString());
        // }
    }
    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void refreshFragment(){
//        Fragment frg = null;
//        frg = getSupportFragmentManager().findFragmentByTag(fragTag);
//        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.detach(frg);
//        ft.attach(frg);
//        ft.commit();
        ///----
//        Fragment currentFragment = this.getFragmentManager().findFragmentById(R.id.recyclerView);
//        if (currentFragment!=null) {
//            FragmentTransaction fragTransaction =   this.getFragmentManager().beginTransaction();
//            fragTransaction.detach(currentFragment);
//            fragTransaction.attach(currentFragment);
//            fragTransaction.commit();
//        }
        PlaceholderFragment.mAdapter.notifyDataSetChanged();
        sectionsPagerAdapter = new SectionsPagerAdapter(MainActivity.this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
    }

}