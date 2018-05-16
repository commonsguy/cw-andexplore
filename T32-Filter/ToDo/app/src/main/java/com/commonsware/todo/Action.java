package com.commonsware.todo;

import com.google.auto.value.AutoValue;

public abstract class Action {
  public static Action add(ToDoModel model) {
    return new AutoValue_Action_Add(model);
  }

  public static Action edit(ToDoModel model) {
    return new AutoValue_Action_Edit(model);
  }

  public static Action delete(ToDoModel model) {
    return new AutoValue_Action_Delete(model);
  }

  public static Action show(ToDoModel model) {
    return new AutoValue_Action_Show(model);
  }

  public static Action load() {
    return new Action.Load();
  }

  public static Action filter(FilterMode mode) {
    return(new AutoValue_Action_Filter(mode));
  }

  @AutoValue
  public static abstract class Add extends Action {
    public abstract ToDoModel model();
  }

  @AutoValue
  public static abstract class Edit extends Action {
    public abstract ToDoModel model();
  }

  @AutoValue
  public static abstract class Delete extends Action {
    public abstract ToDoModel model();
  }

  @AutoValue
  static abstract class Show extends Action {
    public abstract ToDoModel current();
  }

  public static class Load extends Action {

  }

  @AutoValue
  static abstract class Filter extends Action {
    public abstract FilterMode filterMode();
  }
}
