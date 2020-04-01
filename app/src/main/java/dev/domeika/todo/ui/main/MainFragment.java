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
import dev.domeika.todo.database.Todo;

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
                if (!mMainViewModel.getTodos().isEmpty()) {
                    mTodos = mMainViewModel.getTodos();
                }
                updateUI();
            }
        });
    }

    private void updateUI() {
        RecyclerView recyclerView = view.findViewById(R.id.todos_recycler_view);
        recyclerView.setHasFixedSize(true);

        if (mTodos != null) {
            if (!mTodos.isEmpty()) {
                TodoAdapter mAdapter = new TodoAdapter(mTodos, this, this);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            }
        }

        FloatingActionButton btnAddTodo = view.findViewById(R.id.btnAddTodo);
        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoAddFragment todoAddFragment = TodoAddFragment.newInstance();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.container, todoAddFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public void onTodoClick(int position) {
        mMainViewModel.setTodo(mTodos.get(position));
        TodoEditFragment todoEditFragment = TodoEditFragment.newInstance();
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.container, todoEditFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCheckBoxClick(int position) {
        Todo todo = mTodos.get(position);
        todo.setIsComplete(!todo.getIsComplete());
        mMainViewModel.update(todo);
    }
}
