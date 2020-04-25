package dev.domeika.todo.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.UUID;

import dev.domeika.todo.R;
import dev.domeika.todo.models.TodoLocation;

public class LocationSelectionFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPoiClickListener {
    private GoogleMap mMap;
    private MainViewModel mMainViewModel;

    public LocationSelectionFragment() { }

    static LocationSelectionFragment newInstance() {
        return new LocationSelectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_location_selection, container,
                false);

        initializePlaces();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        updateUI(view);

        return view;
    }

    private void updateUI(View view) {
        Button btnAcceptLocation = view.findViewById(R.id.btnAcceptLocation);
        btnAcceptLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void initializePlaces() {
        Places.initialize(getContext(), getString(R.string.google_maps_key));

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
        ));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mMap.clear();
                addMarker(place.getLatLng().latitude, place.getLatLng().longitude, place.getName());

                TodoLocation location = new TodoLocation(
                        place.getId(),
                        place.getName(),
                        place.getLatLng().latitude,
                        place.getLatLng().longitude
                );
                mMainViewModel.setTodoLocation(location);
            }

            @Override
            public void onError(Status status) {
                Log.i("TEST", "An error occurred: " + status);
            }
        });
    }

    private void addMarker(double lat, double lon, String name) {
        LatLng location = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in " + name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnPoiClickListener(this);
        mMap = googleMap;

        if (mMainViewModel.getTodoLocation() != null) {
            addMarker(
                    mMainViewModel.getTodoLocation().getLatitude(),
                    mMainViewModel.getTodoLocation().getLongitude(),
                    mMainViewModel.getTodoLocation().getName()
            );
        } else {
            addMarker(53.801277, -1.548567, "Leeds");
        }
        mMap.setMinZoomPreference(15);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                LatLng chosenPlace = new LatLng(latLng.latitude, latLng.longitude);
                mMap.addMarker(new MarkerOptions().position(chosenPlace).title("Marker in Custom Location"));

                TodoLocation location = new TodoLocation(
                        UUID.randomUUID().toString(),
                        "Custom Location",
                        latLng.latitude,
                        latLng.longitude
                );

                mMainViewModel.setTodoLocation(location);
            }
        });
    }

    @Override
    public void onPoiClick(PointOfInterest poi) {
        mMap.clear();
        LatLng chosenPlace = new LatLng(poi.latLng.latitude, poi.latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(chosenPlace).title("Marker in " + poi.name));

        TodoLocation location = new TodoLocation(
                poi.placeId,
                poi.name,
                poi.latLng.latitude,
                poi.latLng.longitude
        );
        mMainViewModel.setTodoLocation(location);
    }
}
