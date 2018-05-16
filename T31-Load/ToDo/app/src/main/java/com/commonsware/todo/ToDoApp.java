package com.commonsware.todo;

import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;

public class ToDoApp extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

    new Handler().postAtFrontOfQueue(this::enableStrictMode);
  }

  private void enableStrictMode() {
    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .penaltyDeath()
        .build());
    }
    else {
      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .build());
    }
  }
}
