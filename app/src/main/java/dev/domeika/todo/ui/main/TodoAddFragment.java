package dev.domeika.todo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dev.domeika.todo.R;
import dev.domeika.todo.database.Todo;

public class TodoAddFragment extends Fragment {
    private MainViewModel mMainViewModel;
    private FloatingActionButton mBtnAddTodo;

    public static TodoAddFragment newInstance() {
        return new TodoAddFragment();
    }

    public TodoAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_todo_add, container,
                false);
        mBtnAddTodo = view.findViewById(R.id.btnAddTodo);

        updateUI(view);
        return view;
    }

    private void updateUI(View view) {
        final EditText mInputTitle = view.findViewById(R.id.inputTitle);
        final EditText mInputDescription = view.findViewById(R.id.inputDescription);
        mInputTitle.requestFocus();

        mBtnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo todo = new Todo(mInputTitle.getText().toString(),
                        mInputDescription.getText().toString());

                mMainViewModel.insert(todo);

                MainFragment mainFragment = MainFragment.newInstance();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, mainFragment);
                transaction.commitNow();
            }
        });
    }
}
