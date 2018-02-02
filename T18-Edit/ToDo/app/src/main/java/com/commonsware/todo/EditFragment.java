package com.commonsware.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.commonsware.todo.databinding.TodoEditBinding;

public class EditFragment extends Fragment {
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
}
