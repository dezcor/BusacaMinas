package com.example.dezcorjm.buscaminas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

public class Lienzo extends View {

    Paint paint=null;
    Game game=null;
    TextView link;
    Lienzo(Context context)
    {
        super(context);
        paint = new Paint();
        //cuadrado = new Cuadrado(100,200,100);
        //cuadrado.setPaint(paint);
        game = new Game(paint);
    }
    private int px=0;
    private int py=0;
    private boolean Toush=false;

    @Override
    public void onDraw(Canvas canvas)
    {
        if(game.isInit())
        {
            game.set(canvas.getWidth(),canvas.getHeight());
        }
        if(isToush())
        {
            setToush(false);
            if(!game.isGameOver()) {
                if (game.isToush(getPointX(), getPointY())) {
                    game.Gano();
                    link.setText(getNumero());
                }
            }
        }
        //cuadrado.draw(canvas);
        game.Draw(canvas);
    }

    public int getPointX() {
        return px;
    }
    public void setPointX(int px) {
        this.px = px;
    }

    public int getPointY() {
        return py;
    }

    public void setPointY(int py) {
        this.py = py;
    }

    public boolean isToush() {
        return Toush;
    }

    public void setToush(boolean toush) {
        Toush = toush;
    }

    public void ResetDraw()
    {
        invalidate();
    }

    void Reset()
    {
        game.Reset();
    }

    String getNumero()
    {
        return  Integer.toString(game.getNumeroBanderas());
    }

    void Cambio(){
        game.setmBandera(!game.isBandera());
    }

    boolean isActivo() {
        return !game.isBandera();
    }

    void setLink(TextView textView)
    {
        link = textView;
    }
}
