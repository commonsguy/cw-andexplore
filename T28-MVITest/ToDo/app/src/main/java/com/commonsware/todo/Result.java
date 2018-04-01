package com.commonsware.todo;

import com.google.auto.value.AutoValue;
import java.util.Collections;
import java.util.List;

public abstract class Result {
  public static Result added(ToDoModel model) {
    return new AutoValue_Result_Added(model);
  }

  public static Result modified(ToDoModel model) {
    return new AutoValue_Result_Modified(model);
  }

  static Result deleted(ToDoModel model) {
    return new AutoValue_Result_Deleted(model);
  }

  static Result showed(ToDoModel current) {
    return new AutoValue_Result_Showed(current);
  }

  static Result loaded(List<ToDoModel> models) {
    return new AutoValue_Result_Loaded(Collections.unmodifiableList(models));
  }

  @AutoValue
  public static abstract class Added extends Result {
    public abstract ToDoModel model();
  }

  @AutoValue
  public static abstract class Modified extends Result {
    public abstract ToDoModel model();
  }

  @AutoValue
  public static abstract class Deleted extends Result {
    public abstract ToDoModel model();
  }

  @AutoValue
  static abstract class Showed extends Result {
    public abstract ToDoModel current();
  }

  @AutoValue
  public static abstract class Loaded extends Result {
    public abstract List<ToDoModel> models();
  }
}
