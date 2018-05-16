package com.commonsware.todo;

import com.google.auto.value.AutoValue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import android.support.annotation.Nullable;

@AutoValue
public abstract class ToDoModel {
  public abstract String id();
  public abstract boolean isCompleted();
  public abstract String description();
  @Nullable public abstract String notes();
  public abstract Calendar createdOn();

  static Builder builder() {
    return new AutoValue_ToDoModel.Builder();
  }

  public static Builder creator() {
    return builder()
      .isCompleted(false)
      .id(UUID.randomUUID().toString())
      .createdOn(Calendar.getInstance());
  }

  public Builder toBuilder() {
    return builder()
      .id(id())
      .isCompleted(isCompleted())
      .description(description())
      .notes(notes())
      .createdOn(createdOn());
  }

  static final Comparator<ToDoModel> SORT_BY_DESC=
    (one, two) -> (one.description().compareTo(two.description()));

  public static List<ToDoModel> filter(List<ToDoModel> models,
                                       FilterMode filterMode) {
    List<ToDoModel> result;

    if (filterMode==FilterMode.COMPLETED) {
      result=new ArrayList<>();

      for (ToDoModel model : models) {
        if (model.isCompleted()) {
          result.add(model);
        }
      }
    }
    else if (filterMode==FilterMode.OUTSTANDING) {
      result=new ArrayList<>();

      for (ToDoModel model : models) {
        if (!model.isCompleted()) {
          result.add(model);
        }
      }
    }
    else {
      result=new ArrayList<>(models);
    }

    return Collections.unmodifiableList(result);
  }

  @AutoValue.Builder
  public static abstract class Builder {
    abstract Builder id(String id);
    public abstract Builder isCompleted(boolean isCompleted);
    public abstract Builder description(String desc);
    public abstract Builder notes(String notes);
    abstract Builder createdOn(Calendar date);
    public abstract ToDoModel build();
  }
}
