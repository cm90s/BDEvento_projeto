package com.example.gatanhoto.bdevento_projeto.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gatanhoto on 10/16/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected static String TAG = "bdeventos";

    protected void replaceFragment(int container, Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();

    }
}
