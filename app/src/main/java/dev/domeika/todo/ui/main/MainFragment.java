package dev.domeika.todo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import dev.domeika.todo.R;
import dev.domeika.todo.adapter.TodoAdapter;
import dev.domeika.todo.models.Todo;

public class MainFragment extends Fragment implements TodoAdapter.OnTodoClickListener, TodoAdapter.OnCheckBoxClickListener {
    private List<Todo> mTodos;
    private MainViewModel mMainViewModel;
    private View view;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        setupViewModel();
        return view;
    }

    private void setupViewModel() {
        mMainViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);

        mMainViewModel.getLiveDataTodos().observe(getActivity(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> observedTodos) {
                mMainViewModel.setTodos(observedTodos);
                mTodos = mMainViewModel.getTodos();
                updateUI();
            }
        });
    }

    private void updateUI() {
        RecyclerView recyclerView = view.findViewById(R.id.todos_recycler_view);
        recyclerView.setHasFixedSize(true);

        TodoAdapter mAdapter = new TodoAdapter(mTodos, this, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        FloatingActionButton btnAddTodo = view.findViewById(R.id.btnAddTodo);
        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewModel.setTodoLocation(null);
                TodoAddFragment todoAddFragment = TodoAddFragment.newInstance();
                transactFragment(todoAddFragment);
            }
        });
    }

    @Override
    public void onTodoClick(int position) {
        mMainViewModel.setTodo(mTodos.get(position));
        mMainViewModel.setTodoLocation(mTodos.get(position).getLocation());
        TodoEditFragment todoEditFragment = TodoEditFragment.newInstance();
        transactFragment(todoEditFragment);
    }

    @Override
    public void onCheckBoxClick(int position) {
        Todo todo = mTodos.get(position);
        todo.setIsComplete(!todo.getIsComplete());
        mMainViewModel.update(todo);
    }

    private void transactFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
