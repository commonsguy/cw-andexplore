package com.commonsware.todo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities={ToDoEntity.class}, version=1)
@TypeConverters({TypeTransmogrifier.class})
public abstract class ToDoDatabase extends RoomDatabase {
  public abstract ToDoEntity.Store todoStore();

  private static final String DB_NAME="stuff.db";
  private static volatile ToDoDatabase INSTANCE=null;

  synchronized static ToDoDatabase get(Context ctxt) {
    if (INSTANCE==null) {
      INSTANCE=create(ctxt);
    }

    return INSTANCE;
  }

  private static ToDoDatabase create(Context ctxt) {
    RoomDatabase.Builder<ToDoDatabase> b=
      Room.databaseBuilder(ctxt.getApplicationContext(), ToDoDatabase.class,
        DB_NAME);

    return b.build();
  }
}
