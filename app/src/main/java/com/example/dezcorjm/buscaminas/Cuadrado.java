package com.example.dezcorjm.buscaminas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
/*
* Objeto para representar los cuadros
* */
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
    public static final int CUBIERTA = 2;
    Bitmap ImgBandera;
    Bitmap ImgMina;
    Bitmap ImgCubierta;
    Cuadrado()
    {
        mLargo = 10;
        point = new Point();
        rect = new Rect();
        cubierto = true;
    }
    //Constructor
    Cuadrado(int x,int y,int L)
    {
        mLargo = L;
        point = new Point(x, y);
        rect = new Rect(point.x,point.y,point.x+mLargo,point.y+mLargo);
        cubierto = true;
    }

    public void setBandera(Bitmap bandera) {
        ImgBandera=Bitmap.createScaledBitmap(bandera,mLargo-3,mLargo-3,false);
        //this.ImgBandera = bandera;
    }

    public void setMina(Bitmap mina) {
        ImgMina=Bitmap.createScaledBitmap(mina,mLargo-3,mLargo-3,false);
        // /this.ImgMina = mina;
    }

    public void setCubierta(Bitmap cubierta) {
        ImgCubierta=Bitmap.createScaledBitmap(cubierta,mLargo-3,mLargo-3,false);
        //this.ImgCubierta = cubierta;
    }

    public Paint getPaint() {
        return paint;
    }
    //para no tener referencia a la variable paint ya sea una nueva o usada.
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    //cambiar o designar el punto de ubicacion.
    public void setPoint(Point point) {
        this.point = point;
        rect.set(point.x,point.y,point.x+mLargo,point.x+mLargo);
    }
    //cambiar o designar el punto de ubicacion.
    public void setPoint(int x,int y)
    {
        this.point.set(x,y);
        rect.set(point.x,point.y,point.x+mLargo,point.y+mLargo);
    }
    //obtener el punto de ubicacion.
    public Point getPoint() {
        return point;
    }
    //asignar un tamaño
    public void setmLargo(int mLargo) {
        this.mLargo = mLargo;
        rect.set(point.x,point.y,point.x+mLargo,point.y+mLargo);
    }
    //conseguir el tamaño
    public int getmLargo() {
        return mLargo;
    }

    //Metetodos set y get
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
        return rect.contains(x,y) && isCubierto();
    }

    //para poder descubrir el objeto
    void descubir()
    {
        setCubierto(false);
    }
    /*
    * Dibuja los valores contenidos por el objeto
    * Cercania a minas, si tiene mina o nada si esta basia.
    * */
    private void DrawContent(Canvas canvas)
    {
        paint.setTextSize(40);
        if(!isCubierto()) {
            if (isMina()) {
                paint.setARGB(255, 255, 0, 0);
                if(ImgMina == null) {
                    canvas.drawCircle(point.x + mLargo / 2, point.y + mLargo / 2, 10, paint);
                }
                else{
                    canvas.drawBitmap(ImgMina,point.x,point.y,paint);
                }
            } else {
                if (MinasCercanas == 0) return;
                paint.setARGB(255, 255, 255, 255);
                canvas.drawText(Integer.toString(this.MinasCercanas), point.x + mLargo / 2, point.y + mLargo / 2, paint);
            }
        }else if (isBandera()){
            paint.setARGB(255, 0, 0, 255);
            if(ImgBandera==null) {
                canvas.drawRect(point.x + mLargo / 4, point.y + mLargo / 4, point.x + mLargo * 3 / 4, point.y + mLargo * 3 / 4, paint);
            }
            else{
                canvas.drawBitmap(ImgBandera,point.x,point.y,paint);
            }
        }
    }
    /*
    * dibuja el objeto
    * */
    void draw(Canvas canvas)
    {
        if(cubierto) {
            paint.setARGB(255, 25, 25, 25);
            if(ImgCubierta == null && cubierto) {
                canvas.drawRect(rect, paint);
            }else{
                canvas.drawBitmap(ImgCubierta,point.x,point.y,paint);
            }
        }
        else {
            paint.setARGB(255,125,125,125);
            canvas.drawRect(rect, paint);
        }
        DrawContent(canvas);
    }
    /*
    * resetea el objeto
    * */
    void Reset()
    {
        cubierto=true;
        mina = false;
        bandera = false;
        MinasCercanas = 0;
    }
}
