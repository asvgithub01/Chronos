package com.gigigo.asv.chronos;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gigigo.asv.khronos.Base.Action;
import com.gigigo.asv.khronos.Base.Event4Raise;
import com.gigigo.asv.khronos.Manager.AsyncTaskManagerExecutor;
import com.gigigo.asv.khronos.Sample.ActionASV;


public class MainActivityTest extends ActionBarActivity {

    ProgressDialog dialog;

    AsyncTaskManagerExecutor myManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_test);
        /*uso del Await*/
        myManager = new AsyncTaskManagerExecutor(this.getApplicationContext());//asv todo esto no deberia ser asine, el manager deberia ser satic, xo necesita el contxt pa llamar al servicio
        /**/
    }

    public void ClickExecute(View view) {
        //region MOVIDA QUE DEBE ACCEDER AL HILO UI NO CAMBIA
        dialog = ProgressDialog.show(this, "", "LoadingDialog ASYNC EXECUTOR, la tarea se ejecuta/seborra cada vez q se pulse", true);
        dialog.show();
        //endregion
        //region Ejecucion de un proceso asincrono + programación secuencial con Manager + Service +Sticky Values
        myManager.Execute((Action) (new ActionASV(15))).MyOwnEvent4RaiseIT = (new Event4Raise() {
            @Override
            public void onEvent(Object ta) {
                dialog.dismiss();
                dialog = null;
                if (ta != null)
                    NotificarPorUI("onEvent RESULTADO-->" + ta.toString());
                else
                    NotificarPorUI("onEvent RESULTADO-->NULL");
            }

            @Override
            public void onFail(Object ta) {
                if (ta != null)
                    NotificarPorUI( "onFail RESULTADO-->" + ta.toString());
                else
                    NotificarPorUI("onFail RESULTADO-->NULL");
            }

            @Override
            public void onError(Object ta) {
                if (ta != null)
                    NotificarPorUI("onError RESULTADO-->" + ta.toString());
                else
                    NotificarPorUI("onError RESULTADO-->NULL");
            }

        });
        //endregion
    }

    public void ClickExecuteSticky(View view) {

        //region MOVIDA QUE DEBE ACCEDER AL HILO UI NO CAMBIA
        dialog = ProgressDialog.show(this, "", "LoadingDialog ASYNC EXECUTOR, la primera vez tardará la de dios y las subsiguientes 0,", true);
        dialog.show();
        //endregion

        //region Ejecucion de un proceso asincrono + programación secuencial con Manager + Service +Sticky Values
        myManager.ExecuteSticky((Action) (new ActionASV(15))).MyOwnEvent4RaiseIT = (new Event4Raise() {
            @Override
            public void onEvent(Object ta) {
                dialog.dismiss();
                dialog = null;
                if (ta != null)
                    NotificarPorUI("onEvent RESULTADO-->" + ta.toString());
                else
                    NotificarPorUI("onEvent RESULTADO-->NULL");
            }

            @Override
            public void onFail(Object ta) {
                if (ta != null)
                    NotificarPorUI("onFail RESULTADO-->" + ta.toString());
                else
                    NotificarPorUI("onFail RESULTADO-->NULL");
            }

            @Override
            public void onError(Object ta) {
                if (ta != null)
                    NotificarPorUI("onError RESULTADO-->" + ta.toString());
                else
                    NotificarPorUI("onError RESULTADO-->NULL");
            }

        });
//endregion
    }


    private void NotificarPorUI(String msg) {
        try {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

            TextView lblOut = (TextView) findViewById(R.id.lblConsole);
            lblOut.setText(msg);
        }
        catch (Exception ex)
        {
            Log.e("#ERROR", ex.toString());
        }
    }
}
