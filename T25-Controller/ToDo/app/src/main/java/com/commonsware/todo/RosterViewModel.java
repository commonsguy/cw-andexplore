package com.commonsware.todo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import io.reactivex.subjects.PublishSubject;

public class RosterViewModel extends AndroidViewModel {
  private final MutableLiveData<ViewState> states=new MutableLiveData<>();
  private final PublishSubject<Action> actionSubject=
    PublishSubject.create();

  public RosterViewModel(@NonNull Application application) {
    super(application);

    ViewState initial=ViewState.builder().items(ToDoRepository.get().all()).build();

    states.postValue(initial);

    Controller controller=new Controller();

    controller.subscribeToActions(actionSubject);
  }

  public LiveData<ViewState> stateStream() {
    return states;
  }

  public void process(Action action) {
    actionSubject.onNext(action);
  }
}
