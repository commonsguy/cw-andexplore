package com.commonsware.todo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;
import java.util.Calendar;
import java.util.List;

@Entity(tableName="todos", indices=@Index(value="id"))
public class ToDoEntity {
  @PrimaryKey @NonNull final String id;
  @NonNull final String description;
  final String notes;
  final boolean isCompleted;
  @NonNull final Calendar createdOn;

  ToDoEntity(@NonNull String id, @NonNull String description, boolean isCompleted,
             String notes, @NonNull Calendar createdOn) {
    this.id=id;
    this.description=description;
    this.isCompleted=isCompleted;
    this.notes=notes;
    this.createdOn=createdOn;
  }

  @Dao
  public interface Store {
    @Query("SELECT * FROM todos ORDER BY description ASC")
    List<ToDoEntity> all();

    @Insert
    void insert(ToDoEntity... entities);

    @Update
    void update(ToDoEntity... entities);

    @Delete
    void delete(ToDoEntity... entities);
  }
}
