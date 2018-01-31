package com.commonsware.todo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RosterListFragment extends Fragment {
  interface Contract {
    void showModel(ToDoModel model);
  }

  private RecyclerView rv;
  private View empty;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View result=inflater.inflate(R.layout.todo_roster, container, false);

    rv=result.findViewById(R.id.items);
    empty=result.findViewById(R.id.empty);

    return result;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    rv.setLayoutManager(new LinearLayoutManager(getActivity()));

    DividerItemDecoration decoration=new DividerItemDecoration(getActivity(),
      LinearLayoutManager.VERTICAL);

    rv.addItemDecoration(decoration);
    rv.setAdapter(new RosterListAdapter(this));
    empty.setVisibility(View.GONE);
  }
}
