package com.example.dezcorjm.buscaminas;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;
/*
* Contenedor del buscaminas
* */
public class Game {
    //Matrix
    private Cuadrado cuadrado[][];
    private boolean init = true;//Inicio
    private int W = 0;//Dimenciones del lienzo
    private int H = 0;
    private final int Cols = 8;//Dimenciones de la matrix
    private final int Rows = 10;
    private Paint paint;//Para poder dibujar
    private Random random;//Generar numeros aleatorios
    private boolean bandera;//Saber su estado para marcar o descubrir
    private int NumeroMinas=8;//Numero de minas
    private boolean gameover = false;
    private boolean gano = false;
    private int NumeroBanderas=0;//Contar las banderas puestas.
    Bitmap bitbandera;
    Bitmap bitcubierta;
    Bitmap bitmina;
    //Constructor
    Game(Paint p)
    {
        paint = p;
        cuadrado = new Cuadrado[Rows][Cols];
        random = new Random();
        bandera=false;
    }
    //Inicializa el juego con las dimenciones del lienzo
    public void set(int W,int H)
    {
        this.W = W;
        this.H = H;
        int deltaX = W/Cols;
        int deltaY = deltaX;// H/15
        for(int i = 0; i < Rows; i++)
        {
            for(int j = 0; j < Cols; j++)
            {
                cuadrado[i][j] = new Cuadrado(j * deltaX + (j+1),i * deltaY +(i+1),deltaX - 3);
                cuadrado[i][j].setPaint(paint);
                cuadrado[i][j].setBandera(bitbandera);
                cuadrado[i][j].setMina(bitmina);
                cuadrado[i][j].setCubierta(bitcubierta);
            }
        }
        asignarMina(NumeroMinas);
        contarMina();
        setInit(false);
    }
    /*
    * Pone las minas de manera aleatoria evitando las puestas previamente.
    * */
    private void asignarMina(int N)
    {
        int i=0;
        while (i<N)
        {
            int x = random.nextInt(Cols);
            int y = random.nextInt(Rows);
            if(!cuadrado[y][x].isMina())
            {
                cuadrado[y][x].setMina(true);
                i++;
            }
        }
    }

    /*
    * En cada objeto cuenta las minas que tiene de vesinas.
    * */
    private void contarMina()
    {
        for(int i = 0; i < Rows; i++)
        {
            for(int j = 0; j < Cols; j++)
            {
                if(cuadrado[i][j].isMina())
                    continue;
                cuadrado[i][j].setMinasCercanas(count(j,i));
            }
        }
    }
    /*
     * En cada un objeto cuenta las minas que tiene de vesinas.
     * */
    private int count(int x,int y)
    {
        int total = 0;
        if (x - 1 >= 0 && y - 1 >= 0)
            if (cuadrado[y - 1][x - 1].isMina())
                total++;

        if (x - 1 >= 0 )
            if (cuadrado[y][x - 1].isMina())
                total++;

        if (x - 1 >= 0 && y + 1 < Rows)
            if (cuadrado[y+1][x - 1].isMina())
                total++;

        if ( y - 1 >= 0)
            if (cuadrado[y - 1][x].isMina())
                total++;

        if (x + 1 < Cols && y + 1 < Rows)
            if (cuadrado[y + 1][x + 1].isMina())
                total++;

        if (x + 1 < Cols)
            if (cuadrado[y][x + 1].isMina())
                total++;

        if (x + 1 < Cols && y - 1 >= 0)
            if (cuadrado[y - 1][x + 1].isMina())
                total++;

        if (y + 1 < Rows)
            if (cuadrado[y+1][x].isMina())
                total++;

        return total;
    }
    //metodos set y get


    public void setBitbandera(Bitmap bitbandera) {
        this.bitbandera = bitbandera;
    }

    public void setBitmina(Bitmap bitmina) {
        this.bitmina = bitmina;
    }

    public void setBitcubierta(Bitmap bitcubierta) {
        this.bitcubierta = bitcubierta;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void setmBandera(boolean mBandera) {
        this.bandera = mBandera;
    }

    public boolean isBandera() {
        return bandera;
    }

    boolean isGameOver()
    {
        return gameover;
    }

    public void setGameOver(boolean gameover) {
        this.gameover = gameover;
    }

    void setGano(boolean gano)
    {
        this.gano = gano;
    }

    int getNumeroBanderas()
    {
        return NumeroBanderas;
    }

    boolean isGano()
    {
        return gano;
    }
    /*
    * Comprueba que se toco uno de los objetos y dependiendo del estado se intenta hacer un
    * recorrido o se marca el objeto seleccionado.
    * */
    public boolean isToush(int x, int y)
    {
        for(int i = 0; i < Rows; i++)
        {
            for(int j = 0; j < Cols; j++)
            {
                if(cuadrado[i][j].isToush(x,y)) {
                    if(!isBandera())
                        recorrido(j,i);
                    else
                        cuadrado[i][j].setBandera(!cuadrado[i][j].isBandera());
                    return true;
                }
            }
        }
        return false;
    }
    /*Crea un recorrido con todos los objetos que no tienen nada en ellos hasta los objetos que ya
    * son vesinos de alguna mina
    * */
    private void recorrido(int j,int i)
    {
        if (j >= 0 && j < Cols && i >= 0 && i < Rows) {
            if (cuadrado[i][j].isCubierto() && (!cuadrado[i][j].isBandera()) && (cuadrado[i][j].getMinasCercanas() == 0)) {
                cuadrado[i][j].descubir();
                if(cuadrado[i][j].isMina()) {
                    setGameOver(true);
                    return;
                }
                recorrido(j, i + 1);
                recorrido(j, i - 1);
                recorrido(j + 1, i);
                recorrido(j - 1, i);
                recorrido(j - 1, i - 1);
                recorrido(j + 1, i - 1);
                recorrido(j - 1, i + 1);
                recorrido(j + 1, i + 1);
            } else if(!cuadrado[i][j].isBandera()){
                cuadrado[i][j].descubir();
            }
        }
    }
    /*
    * Dibuja cada uno de los objetos
    * Ademas si termino el juego Dibuja el indicador correspondiente.
    * */

    void Draw(Canvas canvas)
    {
        for(int i = 0; i < Rows; i++)
        {
            for(int j = 0; j < Cols; j++)
            {
                cuadrado[i][j].draw(canvas);
            }
        }
        if(isGameOver()) {
            paint.setARGB(160,100,100,100);
            canvas.drawRect(W/4,H/3,W*3/4,H*2/3,paint);
            paint.setARGB(200,255,0,0);
            if(isGano()) {
                paint.setTextSize(80);
                canvas.drawText("You Win",W/3-(W/3)/7,H/2,paint);
            }
            else {
                paint.setTextSize(60);
                canvas.drawText("Game Over",W/3-(W/3)/10,H/2,paint);
            }
        }
    }
    /*
    * comprueba si se a terminado y tambien cuenta los objetos marcados
    * */
    void Gano()
    {
        int N=0;
        NumeroBanderas = 0;
        for(int i = 0; i < Rows; i++)
        {
            for(int j = 0; j < Cols; j++)
            {
                if(cuadrado[i][j].isCubierto())
                {
                    N++;
                }
                if(cuadrado[i][j].isBandera())
                    NumeroBanderas++;
            }
        }
        if(N == this.NumeroMinas) {
            gano = true;
            setGameOver(true);
        }
    }
    //Resetea el juego

    void Reset()
    {
        for(int i = 0; i < Rows; i++)
        {
            for(int j = 0; j < Cols; j++)
            {
                cuadrado[i][j].Reset();
            }
        }
        asignarMina(NumeroMinas);
        contarMina();
        setGameOver(false);
        setGano(false);
        setmBandera(false);
    }

}
