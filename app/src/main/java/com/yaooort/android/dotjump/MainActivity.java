package com.yaooort.android.dotjump;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    DotjumpView dotjumpView1,dotjumpView2;

    ImageView imageView;

    ImageDrawableDot imageDrawableDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dotjumpView1 = findViewById(R.id.dot_view_1);
        dotjumpView1.start();
        dotjumpView2 = findViewById(R.id.dot_view_2);
        dotjumpView2.start();

        imageDrawableDot = new ImageDrawableDot(6);

        imageView = findViewById(R.id.image_view_1);
        imageView.setImageDrawable(imageDrawableDot);
        imageDrawableDot.start();
    }
}
