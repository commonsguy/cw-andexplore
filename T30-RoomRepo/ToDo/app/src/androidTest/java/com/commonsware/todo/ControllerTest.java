/***
 Copyright (c) 2017 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 Covered in detail in the book _Android's Architecture Components_
 https://commonsware.com/AndroidArch
 */

package com.commonsware.todo;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import io.reactivex.subjects.PublishSubject;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ControllerTest {
  @Before
  public void setUp() {
    ToDoDatabase db=ToDoDatabase.get(InstrumentationRegistry.getTargetContext());

    db.todoStore().deleteAll();
  }

  @Test
  public void controller() throws InterruptedException {
    final Controller controller=new Controller(InstrumentationRegistry.getTargetContext());
    final PublishSubject<Action> actionSubject=PublishSubject.create();
    final LinkedBlockingQueue<Result> receivedResults=new LinkedBlockingQueue<>();

    controller.subscribeToActions(actionSubject);
    controller.resultStream().subscribe(receivedResults::offer);

    actionSubject.onNext(Action.load());

    Result.Loaded resultLoaded=
      (Result.Loaded)receivedResults.poll(1, TimeUnit.SECONDS);

    assertEquals(0, resultLoaded.models().size());

    final ToDoModel fooModel=ToDoModel.creator().description("foo").notes("hello, world!").build();

    actionSubject.onNext(Action.add(fooModel));

    Result.Added resultAdded=
      (Result.Added)receivedResults.poll(1, TimeUnit.SECONDS);

    assertEquals(fooModel, resultAdded.model());

    final ToDoModel barModel=ToDoModel.creator().description("bar").build();
    actionSubject.onNext(Action.add(barModel));
    resultAdded=
      (Result.Added)receivedResults.poll(1, TimeUnit.SECONDS);
    assertEquals(barModel, resultAdded.model());

    final ToDoModel gooModel=ToDoModel.creator()
      .description("goo")
      .isCompleted(true)
      .build();
    actionSubject.onNext(Action.add(gooModel));
    resultAdded=
      (Result.Added)receivedResults.poll(1, TimeUnit.SECONDS);
    assertEquals(gooModel, resultAdded.model());

    final ToDoModel mutatedFoo=fooModel.toBuilder().isCompleted(true).build();
    actionSubject.onNext(Action.edit(mutatedFoo));

    Result.Modified resultModified=
      (Result.Modified)receivedResults.poll(1, TimeUnit.SECONDS);

    assertEquals(mutatedFoo, resultModified.model());

    final ToDoModel mutatedBar=barModel.toBuilder().description("bar!").notes("hi!").build();
    actionSubject.onNext(Action.edit(mutatedBar));
    resultModified=
      (Result.Modified)receivedResults.poll(1, TimeUnit.SECONDS);
    assertEquals(mutatedBar, resultModified.model());

    final ToDoModel mutatedGoo=gooModel.toBuilder()
      .description("goo!")
      .isCompleted(false)
      .build();
    actionSubject.onNext(Action.edit(mutatedGoo));
    resultModified=
      (Result.Modified)receivedResults.poll(1, TimeUnit.SECONDS);
    assertEquals(mutatedGoo, resultModified.model());

    actionSubject.onNext(Action.delete(barModel));

    Result.Deleted resultDeleted=
      (Result.Deleted)receivedResults.poll(1, TimeUnit.SECONDS);

    assertEquals(barModel, resultDeleted.model());

    actionSubject.onNext(Action.delete(fooModel));
    resultDeleted=
      (Result.Deleted)receivedResults.poll(1, TimeUnit.SECONDS);
    assertEquals(fooModel, resultDeleted.model());

    actionSubject.onNext(Action.delete(gooModel));
    resultDeleted=
      (Result.Deleted)receivedResults.poll(1, TimeUnit.SECONDS);
    assertEquals(gooModel, resultDeleted.model());
  }
}