package com.commonsware.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.commonsware.todo.databinding.TodoDisplayBinding;

public class DisplayFragment extends Fragment {
  interface Contract {
    void editModel(ToDoModel model);
  }

  private static final String ARG_ID="id";
  private TodoDisplayBinding binding;

  static DisplayFragment newInstance(ToDoModel model) {
    DisplayFragment result=new DisplayFragment();

    if (model!=null) {
      Bundle args=new Bundle();

      args.putString(ARG_ID, model.id());
      result.setArguments(args);
    }

    return(result);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.actions_display, menu);

    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId()==R.id.edit) {
      ((Contract)getActivity()).editModel(binding.getModel());
      return(true);
    }

    return(super.onOptionsItemSelected(item));
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    binding=TodoDisplayBinding.inflate(getLayoutInflater(), container, false);

    return(binding.getRoot());
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ToDoModel model=ToDoRepository.get().find(getModelId());

    binding.setModel(model);
    binding.setCreatedOn(DateUtils.getRelativeDateTimeString(getActivity(),
      model.createdOn().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS,
      DateUtils.WEEK_IN_MILLIS, 0));
  }

  private String getModelId() {
    return(getArguments().getString(ARG_ID));
  }
}