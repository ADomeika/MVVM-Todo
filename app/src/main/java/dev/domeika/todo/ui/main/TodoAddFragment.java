package dev.domeika.todo.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

import dev.domeika.todo.R;
import dev.domeika.todo.models.Todo;
import dev.domeika.todo.models.TodoLocation;

public class TodoAddFragment extends Fragment {
    private MainViewModel mMainViewModel;
    private FloatingActionButton mBtnAddTodo;
    private Button mBtnChooseLocation;

    public static TodoAddFragment newInstance() {
        return new TodoAddFragment();
    }

    public TodoAddFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_todo_add, container,
                false);

        mBtnAddTodo = view.findViewById(R.id.btnAddTodo);
        mBtnChooseLocation = view.findViewById(R.id.btnChooseLocation);

        updateUI(view);
        return view;
    }

    private void updateUI(View view) {
        final TextView tvChosen = view.findViewById(R.id.tvChosen);
        final TextView tvLocation = view.findViewById(R.id.tvLocation);

        if (mMainViewModel.getTodoLocation() != null) {
            tvChosen.setVisibility(View.VISIBLE);
            tvLocation.setText(mMainViewModel.getTodoLocation().getName());
            tvLocation.setVisibility(View.VISIBLE);
        }

        final EditText mInputTitle = view.findViewById(R.id.inputTitle);
        final EditText mInputDescription = view.findViewById(R.id.inputDescription);
        mInputTitle.requestFocus();

        mBtnChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationSelectionFragment locationSelectionFragment = LocationSelectionFragment.newInstance();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.container, locationSelectionFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mBtnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMainViewModel.getTodoLocation() == null) {
                    showToast("Location should be selected");
                } else if (mInputTitle.getText().toString().isEmpty()) {
                    showToast("Title cannot be empty");
                } else if (mInputDescription.getText().toString().isEmpty()) {
                    showToast("Description cannot be empty");
                } else {
                    TodoLocation todoLocation = mMainViewModel.getTodoLocation();
                    Todo todo = new Todo(mInputTitle.getText().toString(),
                            mInputDescription.getText().toString(),
                            todoLocation);

                    mMainViewModel.insert(todo, todoLocation);

                    MainFragment mainFragment = MainFragment.newInstance();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.replace(R.id.container, mainFragment);
                    transaction.commitNow();
                }
            }
        });
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(getContext(),
                message,
                Toast.LENGTH_SHORT);

        toast.show();
    }
}
