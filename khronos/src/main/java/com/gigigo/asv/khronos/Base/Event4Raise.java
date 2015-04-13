package com.gigigo.asv.khronos.Base;

/**
 * Created by Alberto on 17/03/2015.
 */
public interface Event4Raise<T> {
    void onEvent(T ta);

    void onFail(T ta);

    void onError(T ta);
    //asv podemos definir un montón de eventos diferentes, el fundamentalisimo es onEvent que es cuando volvemos a tener acceso al UIThread
    // void onTaskStatusChange(T tas);
    // void onPreXeChute(T tas);//asv trollingtime
    // void onWhateverOccurs(T tas);
}


