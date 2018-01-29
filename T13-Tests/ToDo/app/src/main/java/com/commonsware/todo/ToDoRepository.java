package com.commonsware.todo;

import java.util.ArrayList;
import java.util.List;

public class ToDoRepository {
  private static volatile ToDoRepository INSTANCE=new ToDoRepository();
  private List<ToDoModel> items=new ArrayList<>();

  public synchronized static ToDoRepository get() {
    return INSTANCE;
  }

  private ToDoRepository() {
    items.add(ToDoModel.creator()
      .description("Buy a copy of _Exploring Android_")
      .notes("See https://wares.commonsware.com")
      .isCompleted(true)
      .build());
    items.add(ToDoModel.creator()
      .description("Complete all of the tutorials")
      .build());
    items.add(ToDoModel.creator()
      .description("Write an app for somebody in my community")
      .notes("Talk to some people at non-profit organizations to see what they need!")
      .build());
  }

  public List<ToDoModel> all() {
    return new ArrayList<>(items);
  }
}
