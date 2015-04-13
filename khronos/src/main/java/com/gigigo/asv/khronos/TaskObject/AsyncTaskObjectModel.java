package com.gigigo.asv.khronos.TaskObject;

import android.os.AsyncTask;

import com.gigigo.asv.khronos.Base.Action;
import com.gigigo.asv.khronos.Base.Enum_Tasks_STATUS;
import com.gigigo.asv.khronos.Base.Event4Raise;

/**
 * Created by Alberto on 07/04/2015.
 */
public class AsyncTaskObjectModel extends AsyncTask<Void, Void, Object> {
    public Action task_action2Execute;//asv Rollito marked interfake si no ;)
    public Object task_stickyReturnValue; //T u OBJECT, aun no sé q es más optimal, T no mola por las putas staticas...object hace bien la funcion esperada
    public String task_name_key;
    public Event4Raise<Object> MyOwnEvent4RaiseIT;
    public Enum<Enum_Tasks_STATUS> task_status = Enum_Tasks_STATUS.NOT_RUN_YET;

    public AsyncTaskObjectModel Execute(Action act) {
        this.task_action2Execute = act;
        //  this.task_name_key = getMyInvoker();
        this.task_status = Enum_Tasks_STATUS.PENDIENTE;
        this.task_stickyReturnValue = null;
        this.execute();
        return this;
    }

    @Override
    protected Object doInBackground(Void... nadaEnConcreto) {
        this.task_status = Enum_Tasks_STATUS.RUNNING_FREE_YEAH;
        Object resultT = (Object) task_action2Execute.Execute();
        return resultT;
    }

    @Override
    protected void onPostExecute(Object result) {
        try {

            this.task_status = Enum_Tasks_STATUS.FINITA;
            this.task_stickyReturnValue = result;

            if (this.MyOwnEvent4RaiseIT != null)
                this.MyOwnEvent4RaiseIT.onEvent(result);


        } catch (Exception e) {

            if (this.MyOwnEvent4RaiseIT != null)
                this.MyOwnEvent4RaiseIT.onError((Object) e);
        }
    }

    public Object getStickyReturnValue() {
        //asv la muvi aqui seria tener un exechuter Manager
        return this.task_stickyReturnValue;
    }

    @Override
    protected void onCancelled() {

        this.task_status = Enum_Tasks_STATUS.FINITA;//asv todo igual un nuevo status para el error? aunk tb los trigger me determinan el estatus
        if (this.MyOwnEvent4RaiseIT != null)
            this.MyOwnEvent4RaiseIT = null;

    }

}
