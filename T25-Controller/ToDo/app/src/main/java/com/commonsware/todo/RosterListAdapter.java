package com.commonsware.todo;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.commonsware.todo.databinding.TodoRowBinding;
import java.util.List;

public class RosterListAdapter extends RecyclerView.Adapter<RosterRowHolder> {
  private List<ToDoModel> models;
  final private RosterListFragment host;

  RosterListAdapter(RosterListFragment host) {
    this.host=host;
  }

  @Override
  public RosterRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    TodoRowBinding binding=
      TodoRowBinding.inflate(host.getLayoutInflater(), parent, false);

    return new RosterRowHolder(binding, this);
  }

  @Override
  public void onBindViewHolder(RosterRowHolder holder, int position) {
    holder.bind(models.get(position));
  }

  @Override
  public int getItemCount() {
    return models==null ? 0 : models.size();
  }

  public void replace(ToDoModel model, boolean isChecked) {
    host.replace(model.toBuilder().isCompleted(isChecked).build());
  }

  void showModel(ToDoModel model) {
    ((RosterListFragment.Contract)host.getActivity()).showModel(model);
  }

  void setState(ViewState state) {
    models=state.items();
    notifyDataSetChanged();
  }
}
