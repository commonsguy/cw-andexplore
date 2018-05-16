package com.commonsware.todo;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class RepoTests {
  private ToDoRepository repo;

  @Before
  public void setUp() {
    ToDoDatabase db=ToDoDatabase.get(InstrumentationRegistry.getTargetContext());

    db.todoStore().deleteAll();
    repo=ToDoRepository.get(InstrumentationRegistry.getTargetContext());
    repo.add(ToDoModel.creator()
      .description("Buy a copy of _Exploring Android_")
      .notes("See https://wares.commonsware.com")
      .isCompleted(true)
      .build());
    repo.add(ToDoModel.creator()
      .description("Complete all of the tutorials")
      .build());
    repo.add(ToDoModel.creator()
      .description("Write an app for somebody in my community")
      .notes("Talk to some people at non-profit organizations to see what they need!")
      .build());
  }

  @Test
  public void getAll() {
    assertEquals(3, repo.all().size());
  }

  @Test
  public void add() {
    ToDoModel model=ToDoModel.creator()
      .isCompleted(true)
      .description("foo")
      .build();

    repo.add(model);

    List<ToDoModel> models=repo.all();

    assertEquals(4, models.size());
    assertThat(models, hasItem(model));
  }

  @Test
  public void replace() {
    ToDoModel original=repo.all().get(1);
    ToDoModel edited=original.toBuilder()
      .isCompleted(true)
      .description("Currently on Tutorial #30")
      .build();

    repo.replace(edited);
    assertEquals(3, repo.all().size());
    assertEquals(edited, repo.all().get(1));
  }

  @Test
  public void delete() {
    assertEquals(3, repo.all().size());
    repo.delete(repo.all().get(0));
    assertEquals(2, repo.all().size());
    repo.delete(repo.all().get(0).toBuilder().build());
    assertEquals(1, repo.all().size());
  }
}
