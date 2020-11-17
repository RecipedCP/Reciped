package com.example.recipedcode;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GzKWCbwgF5JFPqyMO4AaLBtyaO1i2834A5Q6w6oq")
                .clientKey("i7r2yTK00C9gddNWvIUGBFN1CJmxQoltemliXirs")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
