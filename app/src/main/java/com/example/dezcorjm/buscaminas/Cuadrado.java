package com.example.dezcorjm.buscaminas;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Cuadrado {
    private Point point = null;
    private Rect rect = null;
    private int mLargo;
    private Paint paint;
    private boolean cubierto;
    private boolean mina = false;
    private boolean bandera = false;
    private int MinasCercanas = 0;
    public static final int BANDERA = 1;
    public static final int MINA = 2;
    public static final int CUBIERTA = 4;
    public static final int GAMEOVER = 8;

    Cuadrado()
    {
        mLargo = 10;
        point = new Point();
        rect = new Rect();
        cubierto = true;
    }

    Cuadrado(int x,int y,int L)
    {
        mLargo = L;
        point = new Point(x, y);
        rect = new Rect(point.x,point.y,point.x+mLargo,point.y+mLargo);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setPoint(Point point) {
        this.point = point;
        rect.set(point.x,point.y,point.x+mLargo,point.x+mLargo);
    }

    public void setPoint(int x,int y)
    {
        this.point.set(x,y);
        rect.set(point.x,point.y,point.x+mLargo,point.y+mLargo);
    }

    public Point getPoint() {
        return point;
    }

    public void setmLargo(int mLargo) {
        this.mLargo = mLargo;
        rect.set(point.x,point.y,point.x+mLargo,point.y+mLargo);
    }

    public int getmLargo() {
        return mLargo;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public boolean isCubierto() {
        return cubierto;
    }

    public boolean isBandera() {
        return bandera;
    }

    public int getMinasCercanas() {
        return MinasCercanas;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public void setCubierto(boolean cubierto) {
        this.cubierto = cubierto;
    }

    public void setMinasCercanas(int minasCercanas) {
        MinasCercanas = minasCercanas;
    }

    public boolean isToush(int x, int y)
    {
        if(rect.contains(x,y))
        {
            cubierto = true;
            return true;
        }
        return false;
    }

    public boolean isToush(int x, int y,int type)
    {
        if(rect.contains(x,y) && isCubierto())
        {
            switch (type)
            {
                case BANDERA:
                    setBandera(!isBandera());
                    break;
                case CUBIERTA:
                    descubir();
            }
            return true;
        }
        return false;
    }

    void descubir()
    {
        setCubierto(false);
    }

    public boolean isGameOver()
    {
        return mina && !cubierto;
    }

    void DrawContent(Canvas canvas)
    {
        paint.setTextSize(40);
        if(isMina()) {
            paint.setARGB(255,255,0,0);
            canvas.drawCircle(point.x + mLargo / 2, point.y + mLargo / 2, 10, paint);
        }else {
            paint.setARGB(255,255,255,255);
            canvas.drawText(Integer.toString(this.MinasCercanas), point.x + mLargo / 2, point.y + mLargo / 2, paint);
        }
    }

    void draw(Canvas canvas)
    {
        if(cubierto) {
            paint.setARGB(255,25,25,25);
        }
        else {
            paint.setARGB(255,125,125,125);
        }
        canvas.drawRect(rect, paint);
        DrawContent(canvas);
    }
}
