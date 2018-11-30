package com.example.dezcorjm.buscaminas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Lienzo extends View {

    Paint paint=null;
    Game game=null;
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
            paint.setARGB(200,255,0,0);
            canvas.drawCircle(getPointX(),getPointY(),30,paint);
            //cuadrado.isToush(getPointX(),getPointY());
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
}
