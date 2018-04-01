package com.commonsware.todo;

import android.support.v7.widget.RecyclerView;
import com.commonsware.todo.databinding.TodoRowBinding;

public class RosterRowHolder extends RecyclerView.ViewHolder {
  final private TodoRowBinding binding;
  private final RosterListAdapter adapter;

  public RosterRowHolder(TodoRowBinding binding, RosterListAdapter adapter) {
    super(binding.getRoot());

    this.binding=binding;
    this.adapter=adapter;
  }

  void bind(ToDoModel model) {
    binding.setModel(model);
    binding.setHolder(this);
    binding.executePendingBindings();
  }

  public void completeChanged(ToDoModel model, boolean isChecked) {
    if (model.isCompleted()!=isChecked) {
      adapter.replace(model, isChecked);
    }
  }

  public void onClick() {
    adapter.showModel(binding.getModel());
  }
}
