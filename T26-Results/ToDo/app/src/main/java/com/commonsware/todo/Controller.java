package com.commonsware.todo;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Controller {
  private final ToDoRepository toDoRepo=ToDoRepository.get();

  public void subscribeToActions(Observable<Action> actionStream) {
    actionStream
      .observeOn(Schedulers.io())
      .subscribe(this::processImpl);
  }

  private void processImpl(Action action) {
    if (action instanceof Action.Add) {
      add(((Action.Add)action).model());
    }
    else if (action instanceof Action.Edit) {
      modify(((Action.Edit)action).model());
    }
    else if (action instanceof Action.Delete) {
      delete(((Action.Delete)action).model());
    }
  }

  private void add(ToDoModel model) {
    toDoRepo.add(model);
  }

  private void modify(ToDoModel model) {
    toDoRepo.replace(model);
  }

  private void delete(ToDoModel toDelete) {
    toDoRepo.delete(toDelete);
  }
}
