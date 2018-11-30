package com.example.dezcorjm.buscaminas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Lienzo lienzo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout layout = findViewById(R.id.main_layout);
        lienzo = new Lienzo(this);

        lienzo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lienzo.setPointX((int)event.getX());
                lienzo.setPointY((int)event.getY());
                lienzo.setToush(true);
                lienzo.ResetDraw();
                return false;
            }
        });
        layout.addView(lienzo);

    }
}
