package com.example.itsalongstory;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.ColorSpace;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.SyncStateContract;
import android.util.Log;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.itsalongstory.ui.main.SectionsPagerAdapter;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.ContentBody;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

//    public void upload() throws Exception {
//        //Url of the server
//        String url = "http://192.168.0.113:8000/incident_upload";
//        HttpClient client = new DefaultHttpClient();
//        HttpPost post = new HttpPost(url);
//        MultipartEntity mpEntity = new MultipartEntity();
//        //Path of the file to be uploaded
//        String filepath = "example.txt";
//        File file = new File(filepath);
//        ContentBody cbFile = new FileBody(file, "text/plain");
//
//        //Add the data to the multipart entity
//        mpEntity.addPart("image", cbFile);
////        mpEntity.addPart("name", new StringBody("Test", Charset.forName("UTF-8")));
////        mpEntity.addPart("data", new StringBody("This is test report", Charset.forName("UTF-8")));
//        post.setEntity(mpEntity);
//        //Execute the post request
//        HttpResponse response1 = client.execute(post);
//        //Get the response from the server
//        HttpEntity resEntity = response1.getEntity();
//        String Response= EntityUtils.toString(resEntity);
//        Log.d("Response:", Response);
//        //Generate the array from the response
//        JSONArray jsonarray = new JSONArray("["+Response+"]");
//        JSONObject jsonobject = jsonarray.getJSONObject(0);
//        //Get the result variables from response
//        String result = (jsonobject.getString("result"));
//        String msg = (jsonobject.getString("msg"));
//        //Close the connection
//        client.getConnectionManager().shutdown();
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay!
            } else {
                //TODO: Do stuff if permission not granted
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}