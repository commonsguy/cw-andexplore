package com.commonsware.todo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RosterListFragment extends Fragment {
  interface Contract {
    void showModel(ToDoModel model);
    void addModel();
  }

  private RecyclerView rv;
  private View progress;
  private TextView empty;
  private RosterViewModel viewModel;
  private RosterListAdapter adapter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setHasOptionsMenu(true);
    viewModel=ViewModelProviders.of(getActivity()).get(RosterViewModel.class);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.actions_roster, menu);

    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.add:
        ((Contract)getActivity()).addModel();
        return true;

      case R.id.all:
        item.setChecked(true);
        viewModel.process(Action.filter(FilterMode.ALL));
        return true;

      case R.id.completed:
        item.setChecked(true);
        viewModel.process(Action.filter(FilterMode.COMPLETED));
        return true;

      case R.id.outstanding:
        item.setChecked(true);
        viewModel.process(Action.filter(FilterMode.OUTSTANDING));
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View result=inflater.inflate(R.layout.todo_roster, container, false);

    rv=result.findViewById(R.id.items);
    empty=result.findViewById(R.id.empty);
    progress=result.findViewById(R.id.progressBar);

    return result;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    rv.setLayoutManager(new LinearLayoutManager(getActivity()));

    DividerItemDecoration decoration=new DividerItemDecoration(getActivity(),
      LinearLayoutManager.VERTICAL);

    rv.addItemDecoration(decoration);
    adapter=new RosterListAdapter(this);
    rv.setAdapter(adapter);
    viewModel.stateStream().observe(this, this::render);
  }

  public void render(ViewState state) {
    adapter.setState(state);

    if (state.isLoaded()) {
      progress.setVisibility(View.GONE);

      if (state.items().size()==0) {
        empty.setVisibility(View.VISIBLE);
        empty.setText(R.string.msg_empty);
      }
      else if (state.filteredItems().size()==0) {
        empty.setVisibility(View.VISIBLE);
        empty.setText(R.string.msg_empty_filtered);
      }
      else {
        empty.setVisibility(View.GONE);
      }
    }
    else {
      progress.setVisibility(View.VISIBLE);
      empty.setVisibility(View.GONE);
    }
  }

  void replace(ToDoModel model) {
    viewModel.process(Action.edit(model));
  }

  void showModel(ToDoModel model) {
    ((RosterListFragment.Contract)getActivity()).showModel(model);
    viewModel.process(Action.show(model));
  }
}
