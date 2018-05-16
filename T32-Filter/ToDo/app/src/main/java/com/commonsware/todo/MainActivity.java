package com.commonsware.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends FragmentActivity
  implements RosterListFragment.Contract, DisplayFragment.Contract,
  EditFragment.Contract {
  private static final String BACK_STACK_SHOW="showModel";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getSupportFragmentManager().findFragmentById(android.R.id.content)==null) {
      getSupportFragmentManager().beginTransaction()
        .add(android.R.id.content, new RosterListFragment())
        .commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.actions, menu);

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId()==R.id.about) {
      startActivity(new Intent(this, AboutActivity.class));
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void showModel(ToDoModel model) {
    getSupportFragmentManager().beginTransaction()
      .replace(android.R.id.content, DisplayFragment.newInstance(model))
      .addToBackStack(BACK_STACK_SHOW)
      .commit();
  }

  @Override
  public void editModel(ToDoModel model) {
    getSupportFragmentManager().beginTransaction()
      .replace(android.R.id.content, EditFragment.newInstance(model))
      .addToBackStack(null)
      .commit();
  }

  @Override
  public void addModel() {
    editModel(null);
  }

  @Override
  public void finishEdit(boolean deleted) {
    hideSoftInput();

    if (deleted) {
      getSupportFragmentManager().popBackStack(BACK_STACK_SHOW,
        FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    else {
      getSupportFragmentManager().popBackStack();
    }
  }

  private void hideSoftInput() {
    if (getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null) {
      ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
  }
}
