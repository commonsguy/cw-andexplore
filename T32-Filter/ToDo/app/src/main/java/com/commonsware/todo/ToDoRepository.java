package com.commonsware.todo;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class ToDoRepository {
  private static volatile ToDoRepository INSTANCE=null;
  private final ToDoDatabase db;

  public synchronized static ToDoRepository get(Context ctxt) {
    if (INSTANCE==null) {
      INSTANCE=new ToDoRepository(ctxt.getApplicationContext());
    }

    return INSTANCE;
  }

  private ToDoRepository(Context ctxt) {
    db=ToDoDatabase.get(ctxt);
  }

  public List<ToDoModel> all() {
    List<ToDoEntity> entities=db.todoStore().all();
    ArrayList<ToDoModel> result=new ArrayList<>(entities.size());

    for (ToDoEntity entity : entities) {
      result.add(entity.toModel());
    }

    return result;
  }

  public void add(ToDoModel model) {
    db.todoStore().insert(ToDoEntity.fromModel(model));
  }

  public void replace(ToDoModel model) {
    db.todoStore().update(ToDoEntity.fromModel(model));
  }

  public void delete(ToDoModel model) {
    db.todoStore().delete(ToDoEntity.fromModel(model));
  }
}
