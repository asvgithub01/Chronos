package com.gigigo.asv.khronos.Base;

/**
 * Created by Alberto on 13/03/2015.
 * Deberia utilizar N tipos T si los tengo q usar, ya que puedo querer un tipo para el constructor y otro tipo para el return
 * falta javear como se javea esto en java
 */
public interface Action<T> {
    void Action(T ta); //chis.ta.co teta, x si estabas dormid@...
    T Execute();
   // T ExecuteWith(T Ta); // puede ser interesante, aunk pasandole el parametro por constructor así hacemos más necesario daggeo
}
