package com.example.happyalpacas;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("oUqIql0yR9a0OpDbhctuU08fADZC77MAOGPhYnDb")
                .clientKey("vgt1eYecHb7MYUS4a6hns7FPHNRXdebd7LFtKWgS")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
