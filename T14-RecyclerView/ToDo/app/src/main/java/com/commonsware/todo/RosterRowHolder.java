package com.commonsware.todo;

import android.support.v7.widget.RecyclerView;
import com.commonsware.todo.databinding.TodoRowBinding;

public class RosterRowHolder extends RecyclerView.ViewHolder {
  final private TodoRowBinding binding;

  public RosterRowHolder(TodoRowBinding binding) {
    super(binding.getRoot());

    this.binding=binding;
  }

  void bind(ToDoModel model) {
    binding.setModel(model);
    binding.executePendingBindings();
  }
}
