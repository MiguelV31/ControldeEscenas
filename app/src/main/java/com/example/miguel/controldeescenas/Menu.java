package com.example.miguel.controldeescenas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Menu extends Escena {

    Rect ayuda, opciones, juego, records;
    int alto, ancho, ancho2;

    public Menu(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu);
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);
        alto = altoPantalla / 7;
        ancho = anchoPantalla / 7;

        juego = new Rect(ancho, alto, ancho * 6, alto * 3);
        opciones = new Rect(ancho, alto * 4, ancho * 2, alto * 5);
        ayuda = new Rect(ancho*5, alto*4, ancho * 6, alto * 5);
        records = new Rect(ancho*3, alto * 4, ancho * 4, alto * 5);
    }


    // Actualizamos la física de los elementos en pantalla
    public void actualizarFisica() {

    }

    // Rutina de dibujo en el lienzo. Se le llamará desde el hilo
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
            c.drawRect(juego, pBoton);
            c.drawRect(ayuda, pBoton);
            c.drawRect(opciones, pBoton);
            c.drawRect(records, pBoton);
            c.drawText("Jugar", juego.centerX(), juego.centerY() + alto / 2, pTexto);
            //c.drawText("Menú", anchoPantalla / 2 + 20, altoPantalla / 5, pTexto);
            //c.drawText("Menú", anchoPantalla / 2, altoPantalla / 5 + 30, pTexto2);

        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    public int onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();        //Obtenemos el índice de la acción
        int pointerID = event.getPointerId(pointerIndex); //Obtenemos el Id del pointer asociado a la acción
        int accion = event.getActionMasked();             //Obtenemos el tipo de pulsación
        switch (accion) {
            case MotionEvent.ACTION_DOWN:           // Primer dedo toca
            case MotionEvent.ACTION_POINTER_DOWN:  // Segundo y siguientes tocan
                break;

            case MotionEvent.ACTION_UP:                     // Al levantar el último dedo
            case MotionEvent.ACTION_POINTER_UP:  // Al levantar un dedo que no es el último
                if (pulsa(juego, event)) return 1;
                else if (pulsa(opciones, event)) return 99;
                else if (pulsa(records, event)) return 98;
                else if (pulsa(ayuda, event)) return 97;
                break;

            case MotionEvent.ACTION_MOVE: // Se mueve alguno de los dedos

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        return idEscena;
    }
}
