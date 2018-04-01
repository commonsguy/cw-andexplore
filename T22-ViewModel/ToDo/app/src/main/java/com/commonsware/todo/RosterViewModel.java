package com.commonsware.todo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public class RosterViewModel extends AndroidViewModel {
  public RosterViewModel(
    @NonNull Application application) {
    super(application);
  }
}
