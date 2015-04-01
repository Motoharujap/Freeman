package com.motoharu.cleaningapp.UserInterface;

import android.app.Activity;
import android.os.Bundle;

import core.Freeman;


public class CoreActivity extends Activity {
    private Freeman mFreeman;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFreeman = new Freeman();
    }
}
