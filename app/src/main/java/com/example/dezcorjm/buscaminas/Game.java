package com.example.dezcorjm.buscaminas;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Game {
    private Cuadrado cuadrado[][];
    private boolean init = true;
    private int W = 0;
    private int H = 0;
    private final int Cols = 8;
    private final int Rows = 10;
    private Paint paint;
    private Random random;
    Game(Paint p)
    {
        paint = p;
        cuadrado = new Cuadrado[Rows][Cols];
        random = new Random();
    }

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
                cuadrado[i][j] = new Cuadrado(j * deltaX + (j+1),i * deltaY +(i+1),deltaX - 2);
                cuadrado[i][j].setPaint(paint);
            }
        }
        asignarMina(10);
        contarMina();
        setInit(false);
    }

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

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    void Draw(Canvas canvas)
    {
        for(int i = 0; i < Rows; i++)
        {
            for(int j = 0; j < Cols; j++)
            {
                cuadrado[i][j].draw(canvas);
            }
        }
    }
}
