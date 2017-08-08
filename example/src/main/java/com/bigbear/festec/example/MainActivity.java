package com.bigbear.festec.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bigbear.bigbear_core.app.BigBear;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(BigBear.getApplication(), "传入Context", Toast.LENGTH_SHORT).show();
    }
}
