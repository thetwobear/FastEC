package com.bigbear.festec.example;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bigbear.bigbear_core.app.BigBear;
import com.bigbear.bigbear_ec.icon.FontEcModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by bear on 2017/8/7/007.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BigBear.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("")
                .configure();
    }


}
