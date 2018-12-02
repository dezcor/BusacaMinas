package com.example.dezcorjm.buscaminas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Lienzo lienzo=null;
    Button btReset;
    Button btBandera;
    TextView contador;

    /*
    * manejo de eventos con los botones
    * */
    View.OnClickListener onClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.Bandera:
                    lienzo.Cambio();
                    if(lienzo.isActivo())
                        btBandera.setText(R.string.NotBandera);
                    else
                        btBandera.setText(R.string.Bandera);
                    break;
                case R.id.Reset:
                    lienzo.Reset();
                    lienzo.ResetDraw();
                    btBandera.setText(R.string.NotBandera);
                    contador.setText("Marcados: 0");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Obtener los elementos
        RelativeLayout layout = findViewById(R.id.main_layout);
        btBandera = findViewById(R.id.Bandera);
        btReset = findViewById(R.id.Reset);
        contador = findViewById(R.id.contador);
        lienzo = new Lienzo(this);

        btBandera.setOnClickListener(onClickListener);
        btReset.setOnClickListener(onClickListener);
        contador.setText("Marcados: 0");
        lienzo.setLink(contador);
        //para detectar los clicks en el lienzo asi como su ubicacion
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
        Bitmap bandera = BitmapFactory.decodeResource(getResources(),R.drawable.bandera);
        Bitmap cubierta = BitmapFactory.decodeResource(getResources(),R.drawable.cubierto);
        Bitmap mina = BitmapFactory.decodeResource(getResources(),R.drawable.mina);
        //añadir el lienzo al layout
        lienzo.setImagenes(bandera,cubierta,mina);
        layout.addView(lienzo);
    }
}
