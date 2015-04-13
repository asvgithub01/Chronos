package com.gigigo.asv.khronos.Sample;

import com.gigigo.asv.khronos.Base.Action;

import java.util.Random;

/**
 * Created by Alberto on 12/03/2015.
 * esto es un runnable un iterator un command o un Action como te salga del pie denominarlo,
 * en resumen es una clase q tiene un metodo que realiza una funcionalidad especifica y reutilizable
 * tb quiere daggerMambito
 */
public class ActionASV implements Action<String> {
    int topeEscalope = 0; //asv asi le podemos poner un limite del carallo la vela y q tarde la leche

    public ActionASV(int param) {
        topeEscalope = param;
    }

    @Override
    public void Action(String ta) {

        //nohagona
    }

    public String Execute() {
        String Result = "";
        //asv vamos hacer una gilipollez q tarde
        for (int i = 0; i < topeEscalope; i++) {
            for (int j = 0; j < topeEscalope; j++) {
                for (int k = 0; k < topeEscalope; k++) {
                    Result = Result + i + "" + j + "" + k;
                    //try{ wait(1000);}catch (InterruptedException ie){}
                }
            }
        }
        Random rnd = new Random();
        int ini = rnd.nextInt(Result.length() - 11);

        if (Result.length() > ini + 10)
            Result = Result.substring(ini, ini+10);
        else
            Result="Condemorenait";
        //asv jeje pa q devuelva muvis diferentes

        return Result;
    }

}
