package com.gigigo.asv.khronos.Base;

import com.gigigo.asv.khronos.TaskObject.AsyncTaskObjectModel;

/**
 * Created by Alberto on 09/04/2015.
 *
 * NO LO USO, cuando determine como crear correctamente el manager, esto es rollo singleton de java
 * rollito static satanic de .net o como está ahora q está pidiendo injection a voces con un constructor con contexto
 */
public interface ManagerExecutor {

    AsyncTaskObjectModel Execute(Action act);

    void RemoveTaskFromCollection(AsyncTaskObjectModel Task4Remove);

    AsyncTaskObjectModel  getTaskIfExistInCollection (String TaskKeyName);

}
