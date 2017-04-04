package john.eventpromoter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "MapActivity";

    private GoogleMap mMap;

    private HashSet<Event> mEventSet;
    private HashMap<String, Event> mEventMap;

    private ArrayList<Marker> mEventMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mEventSet = (HashSet) getIntent().getSerializableExtra("EventSet");
        mEventMap = (HashMap) getIntent().getSerializableExtra("EventMap");

        mEventMarkers = new ArrayList<>();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnInfoWindowClickListener(this);
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
        for (Event e : mEventMap.values()) {
            // TODO: 4/3/17 make sure events don't stack on eachother
            BuildingCodeLocationEnum building = BuildingCodeLocationEnum.valueOf(e.getBuildingCode().substring(0,3));
            double[] gps = building.getGPS();
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(gps[0],gps[1])).title(e.getEventName()));
            marker.setTag(e.getEventID());
            mEventMarkers.add(marker);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(30.286103, -97.739362)));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "A Marker has been clicked");
        Event event = mEventMap.get(marker.getTag().toString());
        Log.d(TAG, "Event Name: " + event.getEventName());
        Intent intent = new Intent(this, EventDisplayActivity.class).putExtra("Event", event);
        startActivity(intent);
    }
}
