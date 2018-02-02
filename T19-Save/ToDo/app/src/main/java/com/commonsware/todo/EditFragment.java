package com.commonsware.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.commonsware.todo.databinding.TodoEditBinding;

public class EditFragment extends Fragment {
  interface Contract {
    void finishEdit();
  }

  private static final String ARG_ID="id";
  private TodoEditBinding binding;

  static EditFragment newInstance(ToDoModel model) {
    EditFragment result=new EditFragment();

    if (model!=null) {
      Bundle args=new Bundle();

      args.putString(ARG_ID, model.id());
      result.setArguments(args);
    }

    return result;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.actions_edit, menu);

    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId()==R.id.save) {
      save();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    binding=TodoEditBinding.inflate(getLayoutInflater(), container, false);

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.setModel(ToDoRepository.get().find(getModelId()));
  }

  private String getModelId() {
    return getArguments().getString(ARG_ID);
  }

  private void save() {
    ToDoModel newModel=binding.getModel().toBuilder()
      .description(binding.desc.getText().toString())
      .notes(binding.notes.getText().toString())
      .isCompleted(binding.isCompleted.isChecked())
      .build();

    ToDoRepository.get().replace(newModel);
    ((Contract)getActivity()).finishEdit();
  }
}
