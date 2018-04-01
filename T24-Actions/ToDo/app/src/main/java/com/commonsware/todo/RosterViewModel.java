package com.commonsware.todo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class RosterViewModel extends AndroidViewModel {
  private final MutableLiveData<ViewState> states=new MutableLiveData<>();

  public RosterViewModel(@NonNull Application application) {
    super(application);

    ViewState initial=ViewState.builder().items(ToDoRepository.get().all()).build();

    states.postValue(initial);
  }

  public LiveData<ViewState> stateStream() {
    return states;
  }
}
