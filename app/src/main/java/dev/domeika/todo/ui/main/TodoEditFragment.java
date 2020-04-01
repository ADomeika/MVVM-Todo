package dev.domeika.todo.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import dev.domeika.todo.R;
import dev.domeika.todo.database.Todo;

public class TodoEditFragment extends Fragment {
    private MainViewModel mMainViewModel;

    public TodoEditFragment() {}

    public static TodoEditFragment newInstance() {
        return new TodoEditFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_edit, container, false);
        mMainViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);

        updateUI(view);

        return view;
    }


    private void updateUI(View view) {
        final Todo mTodo = mMainViewModel.getTodo();

        final EditText mEditTextTitleEdit = view.findViewById(R.id.inputTitleEdit);
        mEditTextTitleEdit.setText(mTodo.getTitle());

        final EditText mEditTextDescriptionEdit = view.findViewById(R.id.inputDescriptionEdit);
        mEditTextDescriptionEdit.setText((mTodo.getDescription()));

        FloatingActionButton mBtnEditTodo = view.findViewById(R.id.btnUpdateTodo);
        mBtnEditTodo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTodo.setTitle(mEditTextTitleEdit.getText().toString());
                mTodo.setDescription(mEditTextDescriptionEdit.getText().toString());

                mMainViewModel.update(mTodo);

                MainFragment mainFragment = MainFragment.newInstance();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.replace(R.id.container, mainFragment);
                transaction.commitNow();
            }
        });

        FloatingActionButton mBtnRemoveTodo = view.findViewById(R.id.btnRemoveTodo);
        mBtnRemoveTodo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mMainViewModel.delete(mTodo);

                MainFragment mainFragment = MainFragment.newInstance();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.replace(R.id.container, mainFragment);
                transaction.commitNow();
            }
        });
    }
}
