package com.example.ehe0_0;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;


public class Loadingscreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loadingscreen);

        File f= new File(Environment.getExternalStorageDirectory()+"/Wallpaper 4k");
        if(!f.exists()){
            f.mkdirs();
            f.mkdir();
        }
//         setTheme(R.style.Theme_AppCompat);
        Handler hendler =new Handler();
        hendler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=(new Intent(Loadingscreen.this,MainActivity.class));
                startActivity(intent);
                finish();

            }
        },2250);

    }
}