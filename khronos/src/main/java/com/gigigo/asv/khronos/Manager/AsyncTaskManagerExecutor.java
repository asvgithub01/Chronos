package com.gigigo.asv.khronos.Manager;

import android.content.Context;
import android.content.Intent;

import com.gigigo.asv.khronos.Base.Action;
import com.gigigo.asv.khronos.Base.Enum_Tasks_STATUS;
import com.gigigo.asv.khronos.Service.MyExecutorIntentService;
import com.gigigo.asv.khronos.TaskObject.AsyncTaskObjectModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto on 07/04/2015.
 */
public class AsyncTaskManagerExecutor {
    static List<AsyncTaskObjectModel> AllTasks = new ArrayList<>();
    static Context mContext;
    //todo quizas deberiamos escuchar el status de las tareas para eliminarlas de la coleccion.
    //se puede ahcer en el Execute un for previo en plan recorre las tareas y las finitas(sin el sticky) te las calzas
    //o hacerlo explicitamente tras el onfinish de la tarea en la propia activity, a gusto del consumidor

    //todo ejemplo wai pa meterle DI
    public AsyncTaskManagerExecutor(Context context) {
        mContext = context;
    }

    public AsyncTaskObjectModel ExecuteSticky(Action act) {
        return Execute(act, true);
    }

    public AsyncTaskObjectModel Execute(Action act) {
        return Execute(act, false);
    }

    public AsyncTaskObjectModel Execute(Action act, boolean isSticky) {
        //buscamos en la coleccion  getMyInvoker();, si existe no hacemos nada y llamamos al servicio(es el servicio el que propaga con el fire del onevent)
        //si no existe creamos el new AsyncTaskObjectModel y llamamos al servicio con un intent(y es el servicio el que hace el execute)

        //asv mierda fuck y fock, aki el getmyInvoker no me devuelve la linea del mainactivity coomo io quiero
        //hay q pasar del 2º tb
        String Task_Key_Name = getMyTaskKeyName(2);
        AsyncTaskObjectModel newAsyncTask = getTaskIfExistInCollection(Task_Key_Name);

        //Intent intent = new Intent(mContext, MyExecutorService.class);
        Intent intent = new Intent(mContext, MyExecutorIntentService.class);
        intent.putExtra("Last_Task_Name", Task_Key_Name);
        intent.putExtra("Is_Task_Sticky", isSticky);

        if (newAsyncTask == null) {
            newAsyncTask = new AsyncTaskObjectModel();
            newAsyncTask.task_action2Execute = act;
            newAsyncTask.task_name_key = Task_Key_Name;
            newAsyncTask.task_status = Enum_Tasks_STATUS.PENDIENTE;
            newAsyncTask.task_stickyReturnValue = null;

            AllTasks.add(newAsyncTask);
        } else {
            //solo llamar al servicio con el name xo sin añadir de nuevo la tarea a la lista
        }
        mContext.startService(intent);
        //mContext.bindService(intent,null,Context.BIND_IMPORTANT);
        return newAsyncTask;
    }
    public static AsyncTaskObjectModel getTaskIfExistInCollection(String TaskKeyName) {
        AsyncTaskObjectModel resultTask = null;
        if (AllTasks != null) {
            for (AsyncTaskObjectModel itemTask : AllTasks) {
                if (itemTask.task_name_key.equals(TaskKeyName)) {
                    resultTask = itemTask;
                    break;
                }
            }
        }
        return resultTask;
    }

    public static void RemoveTaskFromCollection(String TaskKeyName) {
        RemoveTaskFromCollection(getTaskIfExistInCollection(TaskKeyName));
    }

    public static void RemoveTaskFromCollection(AsyncTaskObjectModel Task4Remove) {
        //todo java Garbage collector o similar? o de esta forma ya elimina bien la tarea de memoria?
        //podemos borrar la tarea rigthHereRigthNOw o no, en plan la dejamos viva durante 5 sg x ejemplo por si
        //la queremos volver a invocar y se trate de una tarea que no cambie el resultado(una descarga de una imagen o un doc x ejemplo)
        //pues en vez de borrarla mantenemos su stickyvalue hasta q nos salga del cimbrel y la borremos
        AllTasks.remove(Task4Remove);
    }

    public static String getMyTaskKeyName() {
        return getMyInvoker(0);
    }

    public static String getMyTaskKeyName(int stepsBack) {
        return getMyInvoker(stepsBack);
    }

    private static String getMyInvoker() {
        return getMyInvoker(0);
    }

    /**
     * @param stepsBack es cuantas trazas hacia atras está la traza que buscamos obtener(depende de dnd tenemos la llamada al getInvoker
     * @return por defecto devuelve NiFUCKINGIdeaMan ^_^, el default value mola xo la variable más ;)
     */
    private static String getMyInvoker(int stepsBack) {
        String invokerGüeno = "NiFUCKINGIdeaMan";
        boolean isTheNextOneTheFuckingInvoker = false;


        String whoAmI = AsyncTaskManagerExecutor.class.getName();//es equivalente a poner en harcoded--> "com.gigigo.asv.akaawait.AsyncTaskManagerExecutor";

        Thread thread = Thread.currentThread(); //asv hay mas hilos q delitos de corrupcion tiene el pp...
        //Map<Thread, StackTraceElement[]> allTraces = Thread.getAllStackTraces();
        //todo asv  muy bonito, muy bonito, chorromil hilos...problema para el asv del futuro

        StackTraceElement[] tracesArray = thread.getStackTrace();
        int i = 0;
        //asv atiende, recorremos el array hasta encotrar la primera coincidencia con esta clase(provocada por la llamada al propio getMyInvoker();
        // y el siguiente item del array de la traza es nuestro invoker, aunk si tenemos el getinvoker como en este caso embebido dentro de la clase del manager
        //el invoker q nos interesa es el que produjo la llamada al manager, por eso la truchilla del stepsBack
        for (StackTraceElement stackTrace : tracesArray) {
            if (isTheNextOneTheFuckingInvoker) {
                if (i >= stepsBack) //asv astucia supiner
                    break;
                i++;
                invokerGüeno = stackTrace.toString();
            }
            if (stackTrace.getClassName().equals(whoAmI))
                isTheNextOneTheFuckingInvoker = true;
        }
        return invokerGüeno;
    }
}
