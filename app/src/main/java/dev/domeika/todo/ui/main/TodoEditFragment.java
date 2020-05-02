package dev.domeika.todo.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import dev.domeika.todo.R;
import dev.domeika.todo.models.Todo;
import dev.domeika.todo.models.TodoLocation;

public class TodoEditFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MainViewModel mMainViewModel;

    public TodoEditFragment() {}

    static TodoEditFragment newInstance() {
        return new TodoEditFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_edit, container, false);
        mMainViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        updateUI(view);

        return view;
    }


    private void updateUI(View view) {
        final Todo mTodo = mMainViewModel.getTodo();
        final TodoLocation mTodoLocation = mMainViewModel.getTodoLocation();

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

                returnToMainFragment();
            }
        });

        FloatingActionButton mBtnRemoveTodo = view.findViewById(R.id.btnRemoveTodo);
        mBtnRemoveTodo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mMainViewModel.delete(mTodo, mTodoLocation);

                returnToMainFragment();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(15);
        LatLng location = new LatLng(
                mMainViewModel.getTodoLocation().getLatitude(),
                mMainViewModel.getTodoLocation().getLongitude()
        );
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in " + mMainViewModel.getTodoLocation().getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    private void returnToMainFragment() {
        MainFragment mainFragment = MainFragment.newInstance();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.replace(R.id.container, mainFragment);
        transaction.commitNow();
    }
}
