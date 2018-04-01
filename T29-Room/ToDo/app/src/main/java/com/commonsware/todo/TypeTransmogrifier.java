package com.commonsware.todo;

import android.arch.persistence.room.TypeConverter;
import java.util.Calendar;

public class TypeTransmogrifier {
  @TypeConverter
  public static Long fromCalendar(Calendar date) {
    if (date==null) {
      return null;
    }

    return date.getTimeInMillis();
  }

  @TypeConverter
  public static Calendar toCalendar(Long millisSinceEpoch) {
    if (millisSinceEpoch==null) {
      return null;
    }

    Calendar result=Calendar.getInstance();

    result.setTimeInMillis(millisSinceEpoch);

    return result;
  }
}
