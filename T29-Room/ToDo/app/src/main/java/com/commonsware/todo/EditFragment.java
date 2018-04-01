package com.commonsware.todo;

import android.arch.lifecycle.ViewModelProviders;
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
    void finishEdit(boolean deleted);
  }

  private static final String ARG_ID="id";
  private TodoEditBinding binding;
  private RosterViewModel viewModel;
  private MenuItem deleteMenu;

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
    viewModel=ViewModelProviders.of(getActivity()).get(RosterViewModel.class);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.actions_edit, menu);
    deleteMenu=menu.findItem(R.id.delete);
    deleteMenu.setVisible(getModelId()!=null);

    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId()==R.id.save) {
      save();
      return true;
    }
    else if (item.getItemId()==R.id.delete) {
      delete();
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

    viewModel.stateStream().observe(this, this::render);
  }

  private String getModelId() {
    return getArguments()==null ? null : getArguments().getString(ARG_ID);
  }

  private void render(ViewState state) {
    if (state!=null) {
      if (getModelId()==null) {
        if (deleteMenu!=null) {
          deleteMenu.setVisible(false);
        }
      }
      else {
        ToDoModel model=state.current();

        binding.setModel(model);
      }
    }
  }

  private void save() {
    ToDoModel.Builder builder;

    if (binding.getModel()==null) {
      builder=ToDoModel.creator();
    }
    else {
      builder=binding.getModel().toBuilder();
    }

    ToDoModel newModel=builder
      .description(binding.desc.getText().toString())
      .notes(binding.notes.getText().toString())
      .isCompleted(binding.isCompleted.isChecked())
      .build();

    if (binding.getModel()==null) {
      viewModel.process(Action.add(newModel));
    }
    else {
      viewModel.process(Action.edit(newModel));
    }

    ((Contract)getActivity()).finishEdit(false);
  }

  private void delete() {
    viewModel.process(Action.delete(binding.getModel()));
    ((Contract)getActivity()).finishEdit(true);
  }
}
